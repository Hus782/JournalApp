package com.example.journal22.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class JournalAndEntries {
    @Embedded
    public Journal Journal;
    @Relation(
            parentColumn = "journal_id",
            entityColumn = "journalID"
    )
    public List<Entry> entries;
}
