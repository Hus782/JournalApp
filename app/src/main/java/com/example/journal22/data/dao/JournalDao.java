package com.example.journal22.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.journal22.data.JournalCount;
import com.example.journal22.data.entity.Journal;
import com.example.journal22.data.JournalAndEntries;

import java.util.List;

@Dao
public interface JournalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void createJournal(Journal journal);

    @Query("Select * from JOURNALS")
    LiveData<List<Journal>> getJournalList();

    @Delete
    void deleteJournal(Journal journal);

    @Update
    void UpdateJournal(Journal journal);

    @Transaction
    @Query("SELECT * FROM JOURNALS")
    LiveData<List<JournalAndEntries>> getJournalAndEntries();

    @Transaction
    @Query("SELECT J.*, (SELECT COUNT(E.entry_id) FROM entries E WHERE E.journalID = J.journal_id) AS entries_count FROM journals J")
    LiveData<List<JournalCount>> getCount();

    //@Query("SELECT J.*, (SELECT COUNT(E.entry_id) FROM entries E WHERE E.journalID = :journalID) AS entries_count FROM journals J WHERE J.journal_id = :journalID")

    //
    //@Query("SELECT J.*, (SELECT COUNT(E.entry_id) FROM entries E WHERE E.journalID = :journalID) AS cnt FROM journals J WHERE J.journal_id = :journalID")


}
