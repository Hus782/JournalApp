package com.example.journal22.old;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.journal22.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class calendar_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);


        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar));

        CalendarView calendarView = (CalendarView) findViewById(R.id.mCalendarView);
        calendarView.setEvents(events);


    }
}