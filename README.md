#Week Calendar (Agenda view)

Mainly created for [Odoo CRM](https://github.com/Odoo-mobile/crm) application.

<table align="center">
<tr>
    <th valign="top">
        <img src="https://raw.githubusercontent.com/dpr-odoo/week-calendar/master/screenshots/week_view.png" />
    </th>
    <th valign="top">
        <img src="https://raw.githubusercontent.com/dpr-odoo/week-calendar/master/screenshots/full_month_view.png" />
    </th>
</tr>
</table>

 
How to include ?
----------------

**Step 1.** Add it in your root **build.gradle** at the end of repositories:

```gradle
allprojects {
    repositories {
      ...
      maven { url "https://jitpack.io" }
    }
  }
```

**Step 2.** Add the dependency


```gradle
dependencies {
    compile 'com.github.dpr-odoo:week-calendar:2.0-rc1'
}
```

Add to your view
----------------

```xml
 <com.odoo.calendar.widget.WeekCalendarView
        android:id="@+id/weekCalendarView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

It will be used with other view also,

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.odoo.calendar.widget.WeekCalendarView
        android:id="@+id/weekCalendarView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:fillViewport="true">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">
            <!-- Your view -->
        </LinearLayout>
    </ScrollView>
</LinearLayout>

```

Handle events
-------------

**Date change listener:**

```java
    WeekCalendarView weekCalendarView = (WeekCalendarView) findViewById(R.id.weekCalendarView);
    weekCalendarView.setCalendarDateChangeListener(new CalendarDateChangeListener() {
        @Override
        public void onCalendarDateChange(DateInfo dateInfo) {
            // Perform your action on date
        }
    });
```

**Highlight date**

```java
    WeekCalendarView weekCalendarView = (WeekCalendarView) findViewById(R.id.weekCalendarView);
    weekCalendarView.setCalendarWeekDayHighlightListener(new CalendarWeekDayHighlightListener() {
        @Override
        public boolean canHighlightDate(DateInfo date) {
            // Highlight 5th October
            if(date.dayOfMonth==5 && date.monthOfYear == 10){
                return true;
            }
            return false;
        }
    });
```

## License
    
    The MIT License (MIT)
    
    Copyright (c) 2016 Dharmang Soni
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
