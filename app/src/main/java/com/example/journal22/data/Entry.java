package com.example.journal22.data;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

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
    public long journalID;



    public Entry(int entry_id, String title, String content, String date, long journalID){
        this.entry_id = entry_id;
        this.title = title;
        this.content = content;
        this.journalID = journalID;
        this.date = date;
    }

    @Ignore
    public Entry(String title, String content, String date,long journalID){
        this.title = title;
        this.content = content;
        this.date = date;
        this.journalID = journalID;

    }
    public int getEntry_id(){return this.entry_id;}
    public String getTitle(){return this.title;}
    public String getContent(){return this.content;}
    public String getDate(){return this.date;}
    public long getJournalID(){return this.journalID;}




    public String getWeekDay(){

        String dtStart = this.date;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());
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
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());
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
    public String getTime(){

        String dtStart = this.date;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());
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
}
