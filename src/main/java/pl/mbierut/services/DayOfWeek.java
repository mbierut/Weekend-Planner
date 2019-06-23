package pl.mbierut.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;

@Service
public class DayOfWeek {

    int getDaysUntilWeekendIncl(){
        LocalDate now = LocalDate.now();
        LocalDate mon = now.with(TemporalAdjusters.next(java.time.DayOfWeek.MONDAY));
        return Period.between(now, mon).getDays();
    }
}
