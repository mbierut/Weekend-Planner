package pl.mbierut.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.mbierut.models.LongWeekend;
import pl.mbierut.models.Weather;
import pl.mbierut.models.weatherdata.WeatherData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherSearch {
    private JSONReader jsonReader;
    private DayOfWeek dayOfWeek;
    private LongWeekendChecker longWeekendChecker;
    @Value("${urlWeather}")
    private String urlWeather;
    @Value("${key}")
    private String key;
    @Value("${urlLongWeekend}")
    private String urlLongWeekend;
    private String year = Integer.toString(LocalDate.now().getYear());

    public WeatherSearch(JSONReader jsonReader, DayOfWeek dayOfWeek, LongWeekendChecker longWeekendChecker) {
        this.jsonReader = jsonReader;
        this.dayOfWeek = dayOfWeek;
        this.longWeekendChecker = longWeekendChecker;
    }

    private Weather getWeatherData(String cityName){
        String urlWeatherFull = this.urlWeather + cityName + this.key;
        return jsonReader.parseToWeatherData(urlWeatherFull);

    }

    public String getOutputData(String cityName){
        int[] extraDays;
        int numberOfDays = this.dayOfWeek.getDaysUntilWeekendIncl();
        String urlLongWeekendFull = this.urlLongWeekend + year + "/PL";
        String datePattern = "dd.MM.yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        StringBuilder result = new StringBuilder();

        LongWeekend[] longWeekend = jsonReader.parseToLongWeekendData(urlLongWeekendFull);
        extraDays = longWeekendChecker.getExtraDays(longWeekend, numberOfDays);

        Weather weather = getWeatherData(cityName);

        //Here we're choosing which data to show the user

        for (int i = Math.max(0, numberOfDays - 2 - extraDays[1]); i < numberOfDays + extraDays[0]; i++) {
            WeatherData data = weather.getData().get(i);
            result.append(formatter.format(data.getDatetime())).append(": ").append(System.getProperty("line.separator"))
                    .append(data.getWeather().getDescription()).append(" ")
                    .append(data.getTemp()).append(System.getProperty("line.separator"));
        }

        return result.toString();

    }
}
