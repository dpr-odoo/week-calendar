package com.odoo.calendar.listeners;

import com.odoo.calendar.utils.DateInfo;

public interface CalendarDateChangeListener {

    void onCalendarDateChange(DateInfo dateInfo);
}
