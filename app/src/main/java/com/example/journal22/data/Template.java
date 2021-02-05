package com.example.journal22.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "templates")
public class Template {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "content")
    private String content;


    public Template(int id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Ignore
    public Template(String title, String content){
        this.title = title;
        this.content = content;
    }


    public int getId(){return this.id;}
    public String getTitle(){return this.title;}
    public String getContent(){return this.content;}
}
