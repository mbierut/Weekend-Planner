package pl.mbierut.models;

import lombok.Data;
import pl.mbierut.models.weatherdata.WeatherData;

import java.util.List;

@Data
public class Weather {
    List<WeatherData> data;
    String city_name;
    double lon;
    String timezone;
    double lat;
    String country_code;
    int state_code;
}
