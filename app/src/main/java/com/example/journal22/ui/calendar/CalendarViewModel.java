package com.example.journal22.ui.calendar;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applandeo.materialcalendarview.EventDay;
import com.example.journal22.R;
import com.example.journal22.data.entity.Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalendarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public List<EventDay> getCalendars(List<Entry> words) {
        List<Calendar> calendars = new ArrayList<>();
        List<EventDay> events = new ArrayList<>();

        for (int i=0;i<words.size();i++){
            Log.v("Date",words.get(i).getDate());

            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());

            Date myDate;

            try {
                myDate = inputFormat.parse(words.get(i).getDate());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(myDate);
                //calendars.add(calendar);
                 events.add(new EventDay(calendar, R.drawable.today_circle_background));
                //  Log.v("Just Date Size", String.valueOf(events.size()));


            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return events;
    }

}