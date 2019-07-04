package pl.mbierut.services;

import org.junit.Test;

import static org.junit.Assert.*;

public class DayOfWeekTest {
    DayOfWeek dayOfWeek = new DayOfWeek();

    @Test
    public void daysShouldBePositiveLessThanEight() {
        assertTrue(dayOfWeek.getDaysUntilWeekendIncl() > 0 && dayOfWeek.getDaysUntilWeekendIncl() < 8);
    }
}