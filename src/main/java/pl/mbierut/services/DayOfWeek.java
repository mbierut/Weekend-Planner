package pl.mbierut.services;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class DayOfWeek {
    private static String now = LocalDate.now().getDayOfWeek().toString();

    int getDaysUntilWeekendIncl(){
        int dayNum = 0;
        switch (now) {
            case "MONDAY":
                dayNum = 7;
                break;
            case "TUESDAY":
                dayNum = 6;
                break;
            case "WEDNESDAY":
                dayNum = 5;
                break;
            case "THURSDAY":
                dayNum = 4;
                break;
            case "FRIDAY":
                dayNum = 3;
                break;
            case "SATURDAY":
                dayNum = 2;
                break;
            case "SUNDAY":
                dayNum = 1;
                break;
        }
        return dayNum;
    }
}
