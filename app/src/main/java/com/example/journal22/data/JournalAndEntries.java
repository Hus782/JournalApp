package com.example.journal22.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.journal22.data.entity.Entry;

import java.util.List;

public class JournalAndEntries {
    @Embedded
    public com.example.journal22.data.entity.Journal Journal;
    @Relation(
            parentColumn = "journal_id",
            entityColumn = "journalID"
    )
    public List<Entry> entries;
}
