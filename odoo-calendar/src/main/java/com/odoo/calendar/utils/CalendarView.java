package com.odoo.calendar.utils;

import android.animation.LayoutTransition;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.odoo.calendar.R;
import com.odoo.calendar.listeners.CalendarDateChangeListener;
import com.odoo.calendar.listeners.CalendarWeekDayFilterListener;
import com.odoo.calendar.listeners.OnMonthChangeListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarView extends ViewPager {

    public static final String TAG = CalendarView.class.getSimpleName();
    private Context mContext;
    private SysCalUtils calendar;
    private View currentWeekView, currentMonthView;
    private int activeYear = -1;
    private int focusDay = -1;
    private View recentClicked = null;
    private boolean isMonthView = false;
    private View titleView;
    private int currentWeekOfTheYear = -1;
    private CalendarWeekDayFilterListener mCalendarWeekDayFilterListener;
    private CalendarDateChangeListener mCalendarDateChangeListener;
    private OnMonthChangeListener mOnMonthChangeListener;

    public CalendarView(Context context) {
        super(context);
        init(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            mContext = context;
            calendar = new SysCalUtils();
            activeYear = calendar.getYear();
            post(new Runnable() {
                @Override
                public void run() {
                    bindView();
                }
            });
        }
    }

    private void bindView() {
        OdooCalendarAdapter adapter = new OdooCalendarAdapter();
        setOffscreenPageLimit(calendar.getWeeksOfTheYear());
        setAdapter(adapter);
        addOnPageChangeListener(pageChangeListener);
        focusOnWeek(calendar.getWeekOfYear(activeYear));
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        // Setting current item to current week
        setCurrentItem(calendar.getWeekOfYear(activeYear));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void toggleWeekMonthView() {
        isMonthView = !isMonthView;
        OdooCalendarAdapter adapter = (OdooCalendarAdapter) getAdapter();
        adapter.bindViews();
        focusOnMonth(currentWeekOfTheYear);
    }

    private class OdooCalendarAdapter extends PagerAdapter {

        private List<View> views = new ArrayList<>();

        public OdooCalendarAdapter() {
            bindViews();
        }

        public void bindViews() {
            views.clear();
            if (!isMonthView) {
                int totalWeeks = 53;
                views.add(0, getWeekView(0));
                for (int i = 1; i <= totalWeeks; i++) {
                    views.add(i, getWeekView(i));
                }
                views.add(totalWeeks + 1, getWeekView(totalWeeks + 1));
            } else {
                int totalMonths = 12;
                views.add(0, getMonthView(0));
                for (int i = 1; i <= totalMonths; i++) {
                    views.add(i, getMonthView(i));
                }
                views.add(totalMonths + 1, getMonthView(totalMonths + 1));
            }
            notifyDataSetChanged();
        }

        private View getWeekView(int week) {
            LinearLayout weekView = (LinearLayout) LayoutInflater.from(mContext)
                    .inflate(R.layout.calendar_week_view, null, false);
            bindWeekView(weekView, week);
            weekView.setLayoutTransition(new LayoutTransition());
            return weekView;
        }

        private View getMonthView(int month) {
            int[] weeks = calendar.getWeeksOfTheMonth(activeYear, month);
            LinearLayout parent = new LinearLayout(mContext);
            parent.setOrientation(LinearLayout.VERTICAL);
            for (int i = weeks[0]; i <= weeks[1]; i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.calendar_week_view, null, false);
                bindWeekView(view, i);
                parent.addView(view);
            }
            return parent;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            int index = views.indexOf(object);
            if (index == -1)
                return POSITION_NONE;
            else
                return index;
        }

        public View getView(int position) {
            return views.get(position);
        }

    }

    public void bindWeekTitles(View view) {
        titleView = view;
    }

    private void bindWeekView(View view, int weekOfYear) {
        TextView monTitle, tueTitle, wedTitle, thuTitle, friTitle, satTitle, sunTitle;
        monTitle = (TextView) titleView.findViewById(R.id.monTitle);
        tueTitle = (TextView) titleView.findViewById(R.id.tueTitle);
        wedTitle = (TextView) titleView.findViewById(R.id.wedTitle);
        thuTitle = (TextView) titleView.findViewById(R.id.thuTitle);
        friTitle = (TextView) titleView.findViewById(R.id.friTitle);
        satTitle = (TextView) titleView.findViewById(R.id.satTitle);
        sunTitle = (TextView) titleView.findViewById(R.id.sunTitle);

        monTitle.setText(calendar.getDayDisplayName(1, weekOfYear));
        tueTitle.setText(calendar.getDayDisplayName(2, weekOfYear));
        wedTitle.setText(calendar.getDayDisplayName(3, weekOfYear));
        thuTitle.setText(calendar.getDayDisplayName(4, weekOfYear));
        friTitle.setText(calendar.getDayDisplayName(5, weekOfYear));
        satTitle.setText(calendar.getDayDisplayName(6, weekOfYear));
        sunTitle.setText(calendar.getDayDisplayName(7, weekOfYear));

        TextView monValue, tueValue, wedValue, thuValue, friValue, satValue, sunValue;
        monValue = (TextView) view.findViewById(R.id.monValue);
        tueValue = (TextView) view.findViewById(R.id.tueValue);
        wedValue = (TextView) view.findViewById(R.id.wedValue);
        thuValue = (TextView) view.findViewById(R.id.thuValue);
        friValue = (TextView) view.findViewById(R.id.friValue);
        satValue = (TextView) view.findViewById(R.id.satValue);
        sunValue = (TextView) view.findViewById(R.id.sunValue);

        monValue.setText(calendar.getDayDisplayValue(activeYear, 1, weekOfYear));
        tueValue.setText(calendar.getDayDisplayValue(activeYear, 2, weekOfYear));
        wedValue.setText(calendar.getDayDisplayValue(activeYear, 3, weekOfYear));
        thuValue.setText(calendar.getDayDisplayValue(activeYear, 4, weekOfYear));
        friValue.setText(calendar.getDayDisplayValue(activeYear, 5, weekOfYear));
        satValue.setText(calendar.getDayDisplayValue(activeYear, 6, weekOfYear));
        sunValue.setText(calendar.getDayDisplayValue(activeYear, 7, weekOfYear));
    }

    public void focusOnWeek(int weekOfYear) {
        currentWeekOfTheYear = weekOfYear;
        OdooCalendarAdapter adapter = (OdooCalendarAdapter) getAdapter();
        currentWeekView = adapter.getView(weekOfYear);

        View[] days = new View[7];
        for (int i = 1; i <= 7; i++) {
            days[i - 1] = getDayView(i);
            days[i - 1].setTag(calendar.getDateInfo(activeYear, i, weekOfYear));
            days[i - 1].setOnClickListener(dayClick);
        }
        focusOnDay(calendar.getDayOfWeek());
    }

    public void focusOnMonth(int weekOfYear) {
        OdooCalendarAdapter adapter = (OdooCalendarAdapter) getAdapter();
        int month = calendar.getMonthOfYear(activeYear, weekOfYear);
        currentMonthView = adapter.getView(month);
        setCurrentItem(month, false);
        Log.v(">>>", month + " >>> " + currentMonthView);
        focusOnDay(calendar.getDayOfWeek());
    }

    private View getDayView(int day) {
        switch (day) {
            case 1:
                return currentWeekView.findViewById(R.id.dayMonday);
            case 2:
                return currentWeekView.findViewById(R.id.dayTuesday);
            case 3:
                return currentWeekView.findViewById(R.id.dayWednesday);
            case 4:
                return currentWeekView.findViewById(R.id.dayThursday);
            case 5:
                return currentWeekView.findViewById(R.id.dayFriday);
            case 6:
                return currentWeekView.findViewById(R.id.daySaturday);
            case 7:
                return currentWeekView.findViewById(R.id.daySunday);
        }
        return null;
    }

    private void focusOnDay(int day) {
        View view = (View) getDayView(day).getParent();
        View[] dayViews = {
                view.findViewById(R.id.monValue),
                view.findViewById(R.id.tueValue),
                view.findViewById(R.id.wedValue),
                view.findViewById(R.id.thuValue),
                view.findViewById(R.id.friValue),
                view.findViewById(R.id.satValue),
                view.findViewById(R.id.sunValue)
        };

        for (int i = 0; i < dayViews.length; i++) {
            View dayView = dayViews[i];
            DateInfo dateInfo = (DateInfo) ((View) dayView.getParent()).getTag();
            if ((focusDay != -1 && focusDay == i) || (focusDay == -1 && dateInfo.isToday())) {
                // to focus
                dayView.setBackgroundResource(R.drawable.week_day_bg);
                if (recentClicked == null) {
                    dayClick.onClick((View) dayView.getParent());
                }
            } else {
                if (mCalendarWeekDayFilterListener != null) {
                    if (mCalendarWeekDayFilterListener.hasDataForDate(dateInfo)) {
                        dayView.setBackgroundResource(R.drawable.week_day_bg_data);
                    } else {
                        dayView.setBackgroundResource(R.drawable.week_day_bg_none);
                    }
                } else {
                    dayView.setBackgroundResource(R.drawable.week_day_bg_none);
                }
            }

        }
    }

    OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            OdooCalendarAdapter adapter = (OdooCalendarAdapter) getAdapter();
            if (!isMonthView) {
                int weekDay = calendar.getWeekDaysDiff(activeYear);
                int nextYearPosition = (weekDay % 7 == 0) ? adapter.getCount() - 1 : adapter.getCount() - 2;
                if (position == 0) {
                    activeYear = activeYear - 1;
                    adapter.bindViews();
                    weekDay = calendar.getWeekDaysDiff(activeYear);
                    nextYearPosition = (weekDay % 7 == 0) ? adapter.getCount() - 1 : adapter.getCount() - 2;
                    setCurrentItem(nextYearPosition - 1, false);
                    return;
                } else if (position == nextYearPosition) {
                    activeYear = activeYear + 1;
                    adapter.bindViews();
                    setCurrentItem(1, false);
                    return;
                }
                recentClicked = null;
                focusOnWeek(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    OnClickListener dayClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (recentClicked == view) {
                return;
            }
            recentClicked = view;
            DateInfo dateInfo = (DateInfo) view.getTag();
            if (mOnMonthChangeListener != null) {
                mOnMonthChangeListener.onMonthChange(dateInfo);
            }
            focusDay = dateInfo.dayOfWeek - 1;
            focusOnDay(dateInfo.dayOfWeek);
            if (mCalendarDateChangeListener != null) {
                mCalendarDateChangeListener.onCalendarDateChange(dateInfo);
            }
        }
    };

    // Returns first day of the week (SUNDAY or MONDAY)
    public int getFirstDayOfWeek() {
        return Calendar.getInstance(Locale.getDefault()).getFirstDayOfWeek();
    }

    public void gotoToday() {
        activeYear = calendar.getYear();
        setCurrentItem(calendar.getWeekOfYear(activeYear) - 1, true);
        focusDay = -1;
        recentClicked = null;
        focusOnWeek(calendar.getWeekOfYear(activeYear));
    }

    public void setCalendarDateChangeListener(CalendarDateChangeListener callback) {
        mCalendarDateChangeListener = callback;
    }

    public void setCalendarWeekDayFilterListener(CalendarWeekDayFilterListener callback) {
        mCalendarWeekDayFilterListener = callback;
    }

    public void setOnMonthChangeListener(OnMonthChangeListener callback) {
        mOnMonthChangeListener = callback;
    }
}
