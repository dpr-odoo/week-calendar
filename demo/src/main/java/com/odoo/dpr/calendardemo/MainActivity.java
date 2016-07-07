package com.odoo.dpr.calendardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.odoo.calendar.listeners.CalendarDateChangeListener;
import com.odoo.calendar.listeners.CalendarWeekDayFilterListener;
import com.odoo.calendar.utils.DateInfo;
import com.odoo.calendar.widget.OdooCalendar;

public class MainActivity extends AppCompatActivity implements CalendarDateChangeListener, CalendarWeekDayFilterListener {

    private OdooCalendar odooCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        odooCalendar = (OdooCalendar) findViewById(R.id.odooCalendar);
        odooCalendar.setCalendarDateChangeListener(this);
        odooCalendar.setCalendarWeekDayFilterListener(this);
    }

    @Override
    public void onCalendarDateChange(DateInfo dateInfo) {
        TextView date = (TextView) findViewById(R.id.selectedDate);
        date.setText(dateInfo.toString());
    }

    @Override
    public boolean hasDataForDate(DateInfo date) {
        return (date.dayOfMonth == 7 && date.monthOfYear == 7) ||
                (date.dayOfMonth == 25 && date.monthOfYear == 7);
    }
}
