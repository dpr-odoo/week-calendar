package com.dpr.calendar.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpr.calendar.R;
import com.dpr.calendar.listeners.CalendarDateChangeListener;
import com.dpr.calendar.listeners.CalendarWeekDayHighlightListener;
import com.dpr.calendar.listeners.OnMonthChangeListener;
import com.dpr.calendar.utils.CalendarView;
import com.dpr.calendar.utils.DateInfo;

public class WeekCalendarView extends LinearLayout implements View.OnClickListener, OnMonthChangeListener {
    public static final String TAG = WeekCalendarView.class.getSimpleName();

    private View view;
    private CalendarView calendarView;

    public WeekCalendarView(Context context) {
        super(context);
        init(context);
    }

    public WeekCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WeekCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WeekCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        if (!isInEditMode()) {
            removeAllViews();
            view = LayoutInflater.from(context).inflate(R.layout.odoo_calendar, WeekCalendarView.this, false);
            view.findViewById(R.id.monthToggle).setOnClickListener(WeekCalendarView.this);
            calendarView = (CalendarView) view.findViewById(R.id.calendarView);
            calendarView.setOnMonthChangeListener(WeekCalendarView.this);
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

    public void setCalendarWeekDayHighlightListener(CalendarWeekDayHighlightListener callback) {
        calendarView.setCalendarWeekDayHighlightListener(callback);
    }

    @Override
    public void onMonthChange(DateInfo dateInfo) {
        TextView monthName = (TextView) view.findViewById(R.id.monthName);
        monthName.setText(dateInfo.getMonthName());
    }
}
