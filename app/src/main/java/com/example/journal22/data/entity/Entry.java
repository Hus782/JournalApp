package com.example.journal22.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.example.journal22.ui.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "entries")
public class Entry {
    @PrimaryKey(autoGenerate = true)
    private int entry_id;

    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "journalID")
    public long journalID;
    @ColumnInfo(name = "mood")
    public int mood;
    @ColumnInfo(name = "wordsCount")
    public long wordsCount;


    public Entry(int entry_id, String title, String content, String date,String time, long journalID, long wordsCount){
        this.entry_id = entry_id;
        this.title = title;
        this.content = content;
        this.journalID = journalID;
        this.date = date;
        this.time = time;

        this.mood = 1;
        this.wordsCount = wordsCount;
    }

    @Ignore
    public Entry(String title, String content, String date,String time,long journalID , long wordsCount){
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;

        this.journalID = journalID;
        this.mood = 1;
        this.wordsCount = wordsCount;

    }

    public int getEntry_id(){return this.entry_id;}
    public String getTitle(){return this.title;}
    public String getContent(){return this.content;}
    public String getDate(){return this.date;}
    public String getTime(){return this.time;}

        public long getJournalID(){return this.journalID;}
    public long getWordsCount(){return this.wordsCount;}





    public String getDateWithoutTime(){

        String dtStart = this.date;
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        Date mydate;
        try {
            mydate = format.parse(dtStart);
            //System.out.println(date);
            //Log.v("TAG","" + mydate);

        } catch (ParseException e) {
            e.printStackTrace();
            return "day";

        }
        String Date = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()).format(mydate);

        return Date;

    }

    public String getWeekDay(){

        String dtStart = this.date;
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        Date mydate;
        try {
            mydate = format.parse(dtStart);
            //System.out.println(date);
            //Log.v("TAG","" + mydate);

        } catch (ParseException e) {
            e.printStackTrace();
            return "day";

        }
        String WeekDay = new SimpleDateFormat("EEEE", Locale.getDefault()).format(mydate);

        return WeekDay;

    }
    public String getDay(){

        String dtStart = this.date;
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        Date mydate;
        try {
            mydate = format.parse(dtStart);

        } catch (ParseException e) {
            e.printStackTrace();
            return "day";

        }
        String Day = new SimpleDateFormat("dd", Locale.getDefault()).format(mydate);

        return Day;

    }

    /*
    public String getTime(){

        String dtStart = this.date;
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        Date mydate;
        try {
            mydate = format.parse(dtStart);

        } catch (ParseException e) {
            e.printStackTrace();
            return "day";

        }
        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(mydate);


        return time;

    }

     */
}
