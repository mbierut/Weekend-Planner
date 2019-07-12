package pl.mbierut.services;

import org.springframework.stereotype.Service;
import pl.mbierut.models.LongWeekend;

import java.time.LocalDate;
import java.time.Period;

@Service
public class LongWeekendChecker {

    int[] getExtraDays(LongWeekend[] longWeekend, int numberOfDays){
        int[] extraDays = {0,  0};
        LocalDate endDate;
        LocalDate startDate;
        Period longWeekendDuration;
        int longWeekendBegin;
        for (LongWeekend weekend : longWeekend) {
            endDate = weekend.getEndDate();
            longWeekendDuration = Period.between(LocalDate.now(), endDate);
            if (longWeekendDuration.getDays() < numberOfDays + 3 && longWeekendDuration.getDays() >= 0 && longWeekendDuration.getMonths() == 0 && longWeekendDuration.getYears() == 0) {
                extraDays[0] = longWeekendDuration.getDays() - numberOfDays;
                startDate = weekend.getStartDate();
                longWeekendBegin = Period.between(LocalDate.now(), startDate).getDays();
                extraDays[1] = Math.max(0, longWeekendBegin);
                return extraDays;
            }
        }
        return extraDays;
    }
}
