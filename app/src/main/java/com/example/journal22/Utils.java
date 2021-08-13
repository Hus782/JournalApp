package com.example.journal22;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
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
