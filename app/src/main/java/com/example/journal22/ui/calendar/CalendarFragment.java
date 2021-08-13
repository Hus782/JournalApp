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
    private List<Calendar> calendars = new ArrayList<>();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mWordViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));

        List<EventDay> events = new ArrayList<>();
        Date testDate;

        mWordViewModel.getAllEntries().observe(getViewLifecycleOwner(), words -> {
            // Update the cached copy of the words in the adapter.
            for (int i=0;i<words.size();i++){
                Log.v("Date",words.get(i).getDate());

                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());

                Date mydate = null;

                try {
                    mydate = inputFormat.parse(words.get(i).getDate());
                    Log.v("MyDate", String.valueOf(mydate.getTime()));
                    Date d = new Date();
                    Log.v("Just Date", String.valueOf(d.getTime()));

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(mydate);
                    events.add(new EventDay(calendar, R.drawable.today_circle_background));
                    Log.v("Just Date Size", String.valueOf(events.size()));


                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

            CalendarView calendarView = (CalendarView) getView().findViewById(R.id.mCalendarView);
            Log.v("Just Date Size", String.valueOf(events.size()));

            calendarView.setEvents(events);

        });


    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

      //  List<Calendar> calendars = new ArrayList<>();
       // CalendarView mCalendarView = root.findViewById(R.id.mCalendarView)
        //mCalendarView.setHighlightedDays(calendars);





/*

       // List<EventDay> events = new ArrayList<>();
        LiveData<List<Entry>> entries = mWordViewModel.getAllEntries();
        List<EventDay> events = new ArrayList<>();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());
        Date mydate = null;

        try {
            mydate = inputFormat.parse(entries.getValue().get(0).getDate());
            Log.v("MyDate", String.valueOf(mydate.getTime()));

        }
        catch (Exception e){
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mydate);
        events.add(new EventDay(calendar, R.drawable.today_circle_background));
*/

     //   Calendar calendar = Calendar.getInstance();
       // events.add(new EventDay(calendar, Resouce)));
       // calendar.add(Calendar.DAY_OF_WEEK, 7);
     //   calendar.setTime(new Date());
     //  events.add(new EventDay(calendar, R.drawable.today_circle_background));
      //  Log.v("Calendar", String.valueOf(calendar.getTime()));

        //calendar.set(2021,9,12);
        CalendarView calendarView = (CalendarView) root.findViewById(R.id.mCalendarView);


        List<Calendar> calendars = new ArrayList<>();
     //   calendars.add(calendar);
       // calendarView.setHighlightedDays(calendars);
        calendarView.setOnDayClickListener(eventDay ->
                Toast.makeText(getContext(),
                        eventDay.getCalendar().getTime().toString() + " "
                                + eventDay.isEnabled(),
                        Toast.LENGTH_SHORT).show());
        //calendarView.setDisabledDays(calendars);




        return root;
    }

    private Calendar getRandomCalendar() {
        Random random = new Random();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, random.nextInt(99));

        return calendar;
    }
}