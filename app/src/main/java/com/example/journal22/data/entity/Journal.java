package com.example.journal22.data.entity;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Entity(tableName = "journals")
public class Journal {
    @PrimaryKey(autoGenerate = true)
    private int journal_id;

    @ColumnInfo(name = "title")
    private String title;
//    @ColumnInfo(name = "date")
//    private String date;



    public Journal(int journal_id, String title){
        this.journal_id = journal_id;
        this.title = title;
//        this.date = date;
    }

    @Ignore
    public Journal(String title){
        this.title = title;
//        this.date = date;
    }
    public int getJournal_id(){return this.journal_id;}
    public String getTitle(){return this.title;}
//    public String getDate(){return this.date;}

}

