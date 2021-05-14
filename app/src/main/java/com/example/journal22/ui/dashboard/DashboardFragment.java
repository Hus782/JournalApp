package com.example.journal22.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.journal22.R;
import com.example.journal22.data.EntryViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private EntryViewModel mWordViewModel;
    private List<Calendar> calendars = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

      //  List<Calendar> calendars = new ArrayList<>();
       // CalendarView mCalendarView = root.findViewById(R.id.mCalendarView)
        //mCalendarView.setHighlightedDays(calendars);


        mWordViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));


        mWordViewModel.getAllWords().observe(getViewLifecycleOwner(), words -> {
            // Update the cached copy of the words in the adapter.
            for (int i=0;i<words.size();i++){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Log.v("Date",words.get(i).getDate());

                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                Date mydate = null;
                Date calDate = null;

                try {
                    mydate = inputFormat.parse(words.get(i).getDate());
                    String WeekDay = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mydate);
                    Log.v("Date",WeekDay);

                    calDate = outputFormat.parse(WeekDay);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //String WeekDay = new SimpleDateFormat("EEEE", Locale.getDefault()).format(mydate);
                Calendar cal = Calendar.getInstance();
                cal.setTime(calDate);
                calendars.add(cal);
/*
                Date date = null;
                try {
                    date = sdf.parse(words.get(i).getTime());
                    Log.v("Date",words.get(i).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                //cal.setTime(date);


 */
            }

        });




        CalendarView calendarView = (CalendarView) root.findViewById(R.id.mCalendarView);
        calendarView.setDisabledDays(calendars);
        Log.v("Date", String.valueOf(calendars.size()));




        return root;
    }
}