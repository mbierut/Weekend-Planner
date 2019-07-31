package pl.mbierut.models.weekend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Weekend {
    private LocalDate startDate;
    private LocalDate endDate;


    public boolean hasOverlapWith(Weekend otherPeriod){
        return !getStartDate().isAfter(otherPeriod.getEndDate()) && !getEndDate().isBefore(otherPeriod.getStartDate());
    }

    public List<LocalDate> getAllDaysInclusive(){
        List<LocalDate> allDates = new ArrayList<>();

        LocalDate temp = startDate;
        while (!temp.isAfter(endDate)){
            allDates.add(temp);
            temp = temp.plusDays(1);
        }
        return allDates;
    }


}
