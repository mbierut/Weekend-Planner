package pl.mbierut.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

@Service
public class LongWeekendChecker {
    int[] check(JSONArray array, int numberOfDays) throws JSONException {
        LocalDate endDate;
        LocalDate startDate;
        int period;
        int periodStart;
        int[] extraDays = {0, 0};
        for (int i = 0; i < array.length(); i++){
            endDate = LocalDate.parse(array.getJSONObject(i).getString("endDate"));
            period = Period.between(LocalDate.now(), endDate).getDays();
            if (period < numberOfDays + 3 && period >= 0){
                extraDays[0] = period - numberOfDays - 1;
                startDate = LocalDate.parse(array.getJSONObject(i).getString("startDate"));
                periodStart = Period.between(LocalDate.now(), startDate).getDays();
                extraDays[1] = Math.max(0, periodStart);
            }
        }
        return extraDays;
    }
}
