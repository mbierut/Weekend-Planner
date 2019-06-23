package pl.mbierut.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public WeatherSearch(JSONReader jsonReader, DayOfWeek dayOfWeek, LongWeekendChecker longWeekendChecker) {
        this.jsonReader = jsonReader;
        this.dayOfWeek = dayOfWeek;
        this.longWeekendChecker = longWeekendChecker;
    }

    public String getData(String cityName) {
        String result;
        int numberOfDays = dayOfWeek.getDaysUntilWeekendIncl();
        int[] extraDays;
        String urlWeatherFull = this.urlWeather + cityName + this.key;
        int year = LocalDate.now().getYear();
        String urlLongWeekendFull = this.urlLongWeekend + year + "/PL";
        try {
            JSONArray longWeekendJSON = jsonReader.readJsonArrFromUrl(urlLongWeekendFull);
            extraDays = longWeekendChecker.check(longWeekendJSON, numberOfDays);
            numberOfDays += extraDays[0];
            urlWeatherFull += numberOfDays;
            JSONObject weatherJSON = jsonReader.readJsonFromUrl(urlWeatherFull);
            int beginDays = extraDays[1];

            result = jsonReader.parseWeatherJSON(weatherJSON, numberOfDays, beginDays);
        } catch (Exception e) {
            e.printStackTrace();
            result = "error";
        }
        return result;
    }
}
