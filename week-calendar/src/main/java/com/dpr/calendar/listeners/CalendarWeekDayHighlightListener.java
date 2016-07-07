package com.dpr.calendar.listeners;

import com.dpr.calendar.utils.DateInfo;

public interface CalendarWeekDayHighlightListener {

    boolean canHighlightDate(DateInfo date);
}
