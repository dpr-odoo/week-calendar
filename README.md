#Odoo Calendar (Agenda view)

Mainly created for [Odoo CRM](https://github.com/Odoo-mobile/crm) application.
 
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
    compile 'com.github.dpr-odoo:odoo-calendar:1.0'
}
```

Add to your view
----------------

```xml
 <com.odoo.calendar.widget.OdooCalendar
        android:id="@+id/odooCalendar"
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

    <com.odoo.calendar.widget.OdooCalendar
        android:id="@+id/odooCalendar"
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
    OdooCalendar odooCalendar = (OdooCalendar) findViewById(R.id.odooCalendar);
    odooCalendar.setCalendarDateChangeListener(new CalendarDateChangeListener() {
        @Override
        public void onCalendarDateChange(DateInfo dateInfo) {
            // Perform your action on date
        }
    });
```

**Highlight date**

```java
    OdooCalendar odooCalendar = (OdooCalendar) findViewById(R.id.odooCalendar);
    odooCalendar.setCalendarWeekDayFilterListener(new CalendarWeekDayFilterListener() {
        @Override
        public boolean hasDataForDate(DateInfo date) {
            // Highlight 5th October
            if(date.dayOfMonth==5 && date.monthOfYear == 10){
                return true;
            }
            return false;
        }
    });
```

#License

MIT - Dharmang Soni
