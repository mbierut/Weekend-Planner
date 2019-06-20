package pl.mbierut.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WeatherSearch {
    private JSONReader jsonReader;
    private DayOfWeek dayOfWeek;
    private LongWeekendChecker longWeekendChecker;

    private WeatherSearch(JSONReader jsonReader, DayOfWeek dayOfWeek, LongWeekendChecker longWeekendChecker) {
        this.jsonReader = jsonReader;
        this.dayOfWeek = dayOfWeek;
        this.longWeekendChecker = longWeekendChecker;
    }

    public String getData(String cityName) {
        String result;
        int dayNum = dayOfWeek.getDaysUntilWeekendIncl();
        int[] addDays;
        String urlWeather = "https://api.weatherbit.io/v2.0/forecast/daily?city=" + cityName + "&key=54d43a833702466db78090a59760084f&lang=pl&days=";
        int year = LocalDate.now().getYear();
        String urlLongWeekend = "https://date.nager.at/Api/v2/LongWeekend/" + year + "/PL";
        try {
            JSONArray longWeekendJSON = jsonReader.readJsonArrFromUrl(urlLongWeekend);
            addDays = longWeekendChecker.check(longWeekendJSON, dayNum);
            dayNum += addDays[0];
            urlWeather += dayNum;
            JSONObject weatherJSON = jsonReader.readJsonFromUrl(urlWeather);
            int beginDays = addDays[1];

            result = jsonReader.parseWeatherJSON(weatherJSON, dayNum, beginDays);
        } catch (Exception e) {
            e.printStackTrace();
            result = "error";
        }
        return result;
    }
}
