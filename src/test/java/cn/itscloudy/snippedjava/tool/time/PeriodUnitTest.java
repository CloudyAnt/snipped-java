package cn.itscloudy.snippedjava.tool.time;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PeriodUnitTest {

    private static final ZoneOffset OFFSET = ZoneOffset.ofHours(8);

    @ParameterizedTest(name = "Get current period of unit: {0}")
    @EnumSource(PeriodUnit.class)
    public void shouldGetCurrentPeriod(PeriodUnit unit) {
        Calendar current = Calendar.getInstance(TimeZone.getTimeZone(OFFSET));
        PeriodUnit.Period currentPeriod = unit.current(OFFSET);
        Instant startInstant = currentPeriod.getStartInstant();
        Instant endInstant = currentPeriod.getEndInstant();

        assertEquals(unit, currentPeriod.unit());
        Instant currentInstant = current.toInstant();
        assertTrue(currentInstant.isAfter(startInstant));
        assertTrue(currentInstant.isBefore(endInstant));
    }

    @Test
    public void shouldGetDayPeriods() {
        // GIVEN
        Calendar currentCalendar = Calendar.getInstance(TimeZone.getTimeZone(OFFSET));
        zero(currentCalendar);
        Instant currentPeriodStart = currentCalendar.toInstant();
        currentCalendar.add(Calendar.DATE, -1);
        Instant previousPeriodStart = currentCalendar.toInstant();
        currentCalendar.add(Calendar.DATE, 2);
        Instant nextPeriodStart = currentCalendar.toInstant();
        currentCalendar.add(Calendar.DATE, 1);
        Instant nextNextPeriodStart = currentCalendar.toInstant();

        // WHEN
        PeriodUnit.Period currentPeriod = PeriodUnit.DAY.current(OFFSET);
        PeriodUnit.Period nextPeriod = currentPeriod.toNext();
        PeriodUnit.Period previousPeriod = currentPeriod.toPrevious();

        // THEN
        assertPeriod(currentPeriod, currentPeriodStart, nextPeriodStart);
        assertPeriod(previousPeriod, previousPeriodStart, currentPeriodStart);
        assertPeriod(nextPeriod, nextPeriodStart, nextNextPeriodStart);
    }

    @Test
    public void shouldGetWeekPeriods() {
        // GIVEN
        Calendar currentCalendar = Calendar.getInstance(TimeZone.getTimeZone(OFFSET));
        currentCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        currentCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        zero(currentCalendar);

        Instant currentPeriodStart = currentCalendar.toInstant();
        currentCalendar.add(Calendar.DATE, -7);
        Instant previousPeriodStart = currentCalendar.toInstant();
        currentCalendar.add(Calendar.DATE, 14);
        Instant nextPeriodStart = currentCalendar.toInstant();
        currentCalendar.add(Calendar.DATE, 7);
        Instant nextNextPeriodStart = currentCalendar.toInstant();

        // WHEN
        PeriodUnit.Period currentPeriod = PeriodUnit.WEEK.current(OFFSET);
        PeriodUnit.Period nextPeriod = currentPeriod.toNext();
        PeriodUnit.Period previousPeriod = currentPeriod.toPrevious();

        // THEN
        assertPeriod(currentPeriod, currentPeriodStart, nextPeriodStart);
        assertPeriod(previousPeriod, previousPeriodStart, currentPeriodStart);
        assertPeriod(nextPeriod, nextPeriodStart, nextNextPeriodStart);
    }

    @Test
    public void shouldGetMonthPeriods() {
        // GIVEN
        Calendar currentCalendar = Calendar.getInstance(TimeZone.getTimeZone(OFFSET));
        currentCalendar.set(Calendar.DATE, 1);
        zero(currentCalendar);

        Instant currentPeriodStart = currentCalendar.toInstant();
        currentCalendar.add(Calendar.MONTH, -1);
        Instant previousPeriodStart = currentCalendar.toInstant();
        currentCalendar.add(Calendar.MONTH, 2);
        Instant nextPeriodStart = currentCalendar.toInstant();
        currentCalendar.add(Calendar.MONTH, 1);
        Instant nextNextPeriodStart = currentCalendar.toInstant();

        // WHEN
        PeriodUnit.Period currentPeriod = PeriodUnit.MONTH.current(OFFSET);
        PeriodUnit.Period nextPeriod = currentPeriod.toNext();
        PeriodUnit.Period previousPeriod = currentPeriod.toPrevious();

        // THEN
        assertPeriod(currentPeriod, currentPeriodStart, nextPeriodStart);
        assertPeriod(previousPeriod, previousPeriodStart, currentPeriodStart);
        assertPeriod(nextPeriod, nextPeriodStart, nextNextPeriodStart);
    }

    private void assertPeriod(PeriodUnit.Period period, Instant expectedStart, Instant expectedEnd) {
        assertStartOfDay(period.getStartInstant());
        assertStartOfDay(period.getEndInstant());
        assertEquals(expectedStart, period.getStartInstant());
        assertEquals(expectedEnd, period.getEndInstant());
    }

    private void assertStartOfDay(Instant instant) {
        ZonedDateTime dateTime = instant.atZone(OFFSET);
        assertEquals(0, dateTime.getHour());
        assertEquals(0, dateTime.getMinute());
        assertEquals(0, dateTime.getSecond());
    }

    private void zero(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

}
