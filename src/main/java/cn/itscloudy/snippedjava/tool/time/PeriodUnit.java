package cn.itscloudy.snippedjava.tool.time;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.function.Consumer;

/**
 * Every unit specify a range of period. The period starts and ends at one day's start
 */
public enum PeriodUnit {
    MONTH(
            c -> c.set(Calendar.DATE, 1),
            c -> c.add(Calendar.MONTH, -1),
            c -> c.add(Calendar.MONTH, 1)
    ),
    WEEK(
            c -> {
                c.setFirstDayOfWeek(Calendar.MONDAY);
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            },
            c -> c.add(Calendar.DATE, -7),
            c -> c.add(Calendar.DATE, 7)
    ),
    DAY(
            c -> {
            },
            c -> c.add(Calendar.DATE, -1),
            c -> c.add(Calendar.DATE, 1));

    private final Consumer<Calendar> latestPeriodZero;
    private final Consumer<Calendar> unitAgoSetter;
    private final Consumer<Calendar> unitLaterSetter;

    PeriodUnit(Consumer<Calendar> latestPeriodZero, Consumer<Calendar> unitAgoSetter,
               Consumer<Calendar> unitLaterSetter) {
        this.latestPeriodZero = latestPeriodZero;
        this.unitAgoSetter = unitAgoSetter;
        this.unitLaterSetter = unitLaterSetter;
    }

    public Period current() {
        return of(Calendar.getInstance());
    }

    public Period current(ZoneId zoneId) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(zoneId));
        return of(calendar);
    }

    public Period of(Calendar calendar) {
        zero(calendar);
        return new Period(calendar, this);
    }

    private void zero(Calendar calendar) {
        latestPeriodZero.accept(calendar);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * The interval is [startInstant, endInstant)
     */
    public static class Period {
        private final PeriodUnit periodUnit;

        private final Calendar calendar;

        private final Instant startInstant;

        private final Instant endInstant;

        private Period(Calendar calendar, PeriodUnit periodUnit) {
            this.periodUnit = periodUnit;
            this.calendar = calendar;

            startInstant = calendar.toInstant();
            periodUnit.unitLaterSetter.accept(calendar);
            endInstant = calendar.toInstant();
        }

        public PeriodUnit unit() {
            return periodUnit;
        }

        /**
         * included
         */
        public Instant getStartInstant() {
            return startInstant;
        }

        /**
         * not included
         */
        public Instant getEndInstant() {
            return endInstant;
        }

        public Period toPrevious() {
            Calendar calendar = (Calendar) this.calendar.clone();
            periodUnit.unitAgoSetter.accept(calendar);
            periodUnit.unitAgoSetter.accept(calendar);
            return new Period(calendar, periodUnit);
        }

        public Period toNext() {
            return new Period((Calendar) this.calendar.clone(), periodUnit);
        }
    }
}
