package com.example.journal22.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

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
}
