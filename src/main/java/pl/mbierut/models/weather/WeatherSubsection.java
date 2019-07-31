package pl.mbierut.models.weather;

import lombok.Data;

@Data
class WeatherSubsection {
    String icon;
    int code;
    String description;

    @Override
    public String toString() {
        return description;
    }
}
