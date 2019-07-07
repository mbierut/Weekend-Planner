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
        LocalDate beginDate;
        int longWeekendEnd;
        int longWeekendBegin;
        for (LongWeekend weekend : longWeekend) {
            endDate = weekend.getEndDate();
            longWeekendEnd = Period.between(LocalDate.now(), endDate).getDays();
            if (longWeekendEnd < numberOfDays + 3 && longWeekendEnd >= 0) {
                extraDays[0] = longWeekendEnd - numberOfDays;
                beginDate = weekend.getBeginDate();
                longWeekendBegin = Period.between(LocalDate.now(), beginDate).getDays();
                extraDays[1] = Math.max(0, longWeekendBegin);
            }
        }
        return extraDays;
    }
}
