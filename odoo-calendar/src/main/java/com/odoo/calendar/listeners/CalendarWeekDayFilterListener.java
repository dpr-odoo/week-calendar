package com.odoo.calendar.listeners;

import com.odoo.calendar.utils.DateInfo;

public interface CalendarWeekDayFilterListener {

    boolean hasDataForDate(DateInfo date);
}
