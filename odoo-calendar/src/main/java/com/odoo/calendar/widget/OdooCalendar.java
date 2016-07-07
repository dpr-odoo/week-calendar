package com.odoo.calendar.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.odoo.calendar.R;
import com.odoo.calendar.listeners.CalendarDateChangeListener;
import com.odoo.calendar.listeners.CalendarWeekDayFilterListener;
import com.odoo.calendar.listeners.OnMonthChangeListener;
import com.odoo.calendar.utils.CalendarView;
import com.odoo.calendar.utils.DateInfo;

public class OdooCalendar extends LinearLayout implements View.OnClickListener, OnMonthChangeListener {
    public static final String TAG = OdooCalendar.class.getSimpleName();

    private View view;
    private CalendarView calendarView;

    public OdooCalendar(Context context) {
        super(context);
        init(context);
    }

    public OdooCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OdooCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OdooCalendar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        if (!isInEditMode()) {
            removeAllViews();
            view = LayoutInflater.from(context).inflate(R.layout.odoo_calendar, OdooCalendar.this, false);
            view.findViewById(R.id.monthToggle).setOnClickListener(OdooCalendar.this);
            calendarView = (CalendarView) view.findViewById(R.id.calendarView);
            calendarView.setOnMonthChangeListener(OdooCalendar.this);
            calendarView.bindWeekTitles(view);
            addView(view);
        }
    }

    @Override
    public void onClick(View view) {
        calendarView.toggleWeekMonthView();
    }

    public void setCalendarDateChangeListener(CalendarDateChangeListener callback) {
        calendarView.setCalendarDateChangeListener(callback);
    }

    public void setCalendarWeekDayFilterListener(CalendarWeekDayFilterListener callback) {
        calendarView.setCalendarWeekDayFilterListener(callback);
    }

    @Override
    public void onMonthChange(DateInfo dateInfo) {
        TextView monthName = (TextView) view.findViewById(R.id.monthName);
        monthName.setText(dateInfo.getMonthName());
    }
}
