package com.example.journal22.ui.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import com.example.journal22.R;
import com.example.journal22.ui.entries.EntryViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private EntryViewModel mWordViewModel;
    private MutableLiveData<EventDay> mEvents = new MutableLiveData<EventDay>();

    // private List<Calendar> calendars = new ArrayList<>();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);



        mWordViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));
        CalendarView calendarView = (CalendarView) root.findViewById(R.id.mCalendarView);


        mWordViewModel.getAllEntries().observe(getViewLifecycleOwner(), words -> {
            List<EventDay> events = calendarViewModel.getCalendars(words);
            calendarView.setEvents(events);

        });



/*
        calendarView.setOnDayClickListener(eventDay ->
                Toast.makeText(getContext(),
                        eventDay.getCalendar().getTime().toString() + " "
                                + eventDay.isEnabled(),
                        Toast.LENGTH_SHORT).show());


 */
        //calendarView.setDisabledDays(calendars);




        return root;
    }
}