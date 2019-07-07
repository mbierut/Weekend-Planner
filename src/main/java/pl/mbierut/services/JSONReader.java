package pl.mbierut.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.mbierut.models.LongWeekend;
import pl.mbierut.models.Weather;

@Service
@AllArgsConstructor
public class JSONReader {
    private RestTemplate restTemplate;

    Weather parseToWeatherData(String url){
        return this.restTemplate.getForObject(url, Weather.class);
    }

    LongWeekend[] parseToLongWeekendData(String url){
        return this.restTemplate.getForObject(url, LongWeekend[].class);
    }
}