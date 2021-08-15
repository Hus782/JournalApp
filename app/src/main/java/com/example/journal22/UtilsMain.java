package com.example.journal22;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UtilsMain {

    public static String getFormattedDate() {
        Date c = Calendar.getInstance().getTime();
        Log.v("TAG","Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());

        String formattedDate = df.format(c);
        Log.v("TAG",formattedDate);
        return formattedDate;
    }
    public static void hideKeyboard(Activity activity) {
        //Check if any views have focus and if no then hide keyboard
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static String changeDateFormat(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());
        Date mydate;
        try {
            mydate = format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return "day";

        }
        String WeekDay = new SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.getDefault()).format(mydate);
        return WeekDay;
    }

    public static long countWords(String string) {
        String trim = string.trim();
        if (trim.isEmpty())
            return 0;
        return trim.split("\\s+").length; // separate string around spaces
    }
}
