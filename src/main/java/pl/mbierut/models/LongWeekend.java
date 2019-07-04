package pl.mbierut.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LongWeekend {
    LocalDate endDate;
    LocalDate beginDate;
    int dayCount;
    boolean needBridgeDay;
}
