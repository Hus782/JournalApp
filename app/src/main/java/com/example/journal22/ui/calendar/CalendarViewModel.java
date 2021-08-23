package com.example.journal22.ui.calendar;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.applandeo.materialcalendarview.EventDay;
import com.example.journal22.R;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.ui.Constants;

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

    public List<EventDay> getCalendars(List<String> days) {
        List<Calendar> calendars = new ArrayList<>();
        List<EventDay> events = new ArrayList<>();

        for (int i=0;i<days.size();i++){
           // Log.v("Date",words.get(i).getDateWithoutTime());

            SimpleDateFormat inputFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());

            Date myDate;

            try {
                myDate = inputFormat.parse(days.get(i));

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