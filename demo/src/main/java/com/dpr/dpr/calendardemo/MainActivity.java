package com.dpr.dpr.calendardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.dpr.calendar.listeners.CalendarDateChangeListener;
import com.dpr.calendar.listeners.CalendarWeekDayHighlightListener;
import com.dpr.calendar.utils.DateInfo;
import com.dpr.calendar.widget.WeekCalendarView;

public class MainActivity extends AppCompatActivity implements CalendarDateChangeListener, CalendarWeekDayHighlightListener {

    private WeekCalendarView weekCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        weekCalendar = (WeekCalendarView) findViewById(R.id.weekCalendar);
        weekCalendar.setCalendarDateChangeListener(this);
        weekCalendar.setCalendarWeekDayHighlightListener(this);
    }

    @Override
    public void onCalendarDateChange(DateInfo dateInfo) {
        Log.v(">>>", dateInfo+ " <<");
        TextView date = (TextView) findViewById(R.id.selectedDate);
        date.setText(dateInfo.toString());
    }

    @Override
    public boolean canHighlightDate(DateInfo date) {
        return (date.dayOfMonth == 7 && date.monthOfYear == 7) ||
                (date.dayOfMonth == 25 && date.monthOfYear == 7);
    }
}
