package com.dpr.calendar.utils;

import java.util.Calendar;
import java.util.Locale;

public class SysCalUtils {

    private Calendar calendar;

    public SysCalUtils() {
        calendar = Calendar.getInstance(Locale.getDefault());
    }

    /**
     * Gets the total weeks of the year (52/53)
     *
     * @return total number of the weeks in the year
     */
    public int getWeeksOfTheYear() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        int ordinalDay = calendar.get(Calendar.DAY_OF_YEAR);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
        return (ordinalDay - weekDay + 10) / 7;
    }

    public int getWeeksOfTheYear(int year) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        int ordinalDay = calendar.get(Calendar.DAY_OF_YEAR);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
        return (ordinalDay - weekDay + 10) / 7;
    }

    public int getWeekDaysDiff(int year) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        return calendar.get(Calendar.DAY_OF_WEEK); // Sunday = 0
    }

    /**
     * Getting week of the current year
     *
     * @return current week number of the year
     */
    public int getWeekOfYear(int year) {
        calendar.set(Calendar.YEAR, year);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public String getMonthDisplayName(int year, int month) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) +
                " " + calendar.get(Calendar.YEAR);
    }

    public String getDayDisplayName(int day, int weekOfYear) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        calendar.set(Calendar.DAY_OF_WEEK, day);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }

    public String getDayDisplayValue(int year, int day, int weekOfYear) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH) + (day - calendar.get(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
        return String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.DAY_OF_MONTH));
    }

    public DateInfo getDateInfo(int year, int day, int weekOfYear) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH) + (day - calendar.get(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.DAY_OF_MONTH, day_of_month);

        DateInfo dateInfo = new DateInfo();
        dateInfo.year = calendar.get(Calendar.YEAR);
        dateInfo.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dateInfo.monthOfYear = calendar.get(Calendar.MONTH) + 1;
        dateInfo.weekOfYear = weekOfYear;
        dateInfo.dayOfWeek = day;
        return dateInfo;
    }

    public int getDayOfWeek() {
        return calendar.get(Calendar.DAY_OF_WEEK) + 1;
    }

    public int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public int getMonthOfYear(int year, int weekOfYear) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        return calendar.get(Calendar.MONTH);
    }

    public int[] getWeeksOfTheMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        int startWeek = calendar.get(Calendar.WEEK_OF_YEAR);

        calendar.set(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        int endWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return new int[]{startWeek, endWeek};
    }
}
