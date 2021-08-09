package com.example.journal22.data;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.entity.Journal;

import java.util.List;

public class JournalCount {
    @Embedded
    public Journal journal;

    @ColumnInfo(name = "entries_count")
    public int entries_count;
}