package com.odoo.calendar.utils;

import java.util.Calendar;
import java.util.Locale;

public class DateInfo {
    public int dayOfMonth, monthOfYear, year, weekOfYear, dayOfWeek;

    public boolean isToday() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        return calendar.get(Calendar.WEEK_OF_YEAR) == weekOfYear
                && calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear, dayOfMonth);
    }

    public String getMonthName() {
        SysCalUtils sysCalUtils = new SysCalUtils();
        return sysCalUtils.getMonthDisplayName(year, monthOfYear - 1);
    }
}
