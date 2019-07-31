package pl.mbierut.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbierut.clients.LongWeekendClient;
import pl.mbierut.clients.WeatherClient;
import pl.mbierut.exceptions.WeatherDataNotFoundException;
import pl.mbierut.models.weather.WeatherData;
import pl.mbierut.models.weekend.Weekend;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WeekendWeatherService {

    private WeatherClient weatherClient;
    private LongWeekendClient longWeekendClient;


    private Weekend getNextWeekend(){
        Weekend weekend = getNextRegularWeekend();
        return adjustForLongWeekend(weekend);
    }

    private Weekend getNextRegularWeekend(){
        LocalDate end = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDate start = end.minusDays(1);
        return new Weekend(start, end);
    }

    private Weekend adjustForLongWeekend(Weekend nextRegularWeekend){
        List<Weekend> longWeekendDataForCurrentYearInPoland = longWeekendClient.getLongWeekendDataForCurrentYearInPoland();
        for (Weekend longWeekend : longWeekendDataForCurrentYearInPoland){
            if (longWeekend.hasOverlapWith(nextRegularWeekend)){
                return longWeekend;
            }
        }
        return nextRegularWeekend;
    }

    public List<WeatherData> getWeatherForWeekend(String cityName){
        Map<LocalDate, WeatherData> weatherData = weatherClient.getWeatherData(cityName);
        List<LocalDate> nextWeekendDays = getNextWeekend().getAllDaysInclusive();
        List<WeatherData> sortedWeatherDataForTheNextWeekend = nextWeekendDays.stream()
                .map(d -> Optional.ofNullable(weatherData.get(d)).orElseThrow(() -> new WeatherDataNotFoundException("Brak danych pogodowych")))
                .sorted(Comparator.comparing(WeatherData::getDatetime))
                .collect(Collectors.toList());

        return sortedWeatherDataForTheNextWeekend;
    }
}
