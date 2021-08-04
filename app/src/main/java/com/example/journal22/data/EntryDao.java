package com.example.journal22.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEntry(Entry entry);

    @Query("Select * from ENTRIES where journalID = :journalID")
    LiveData<List<Entry>> getEntryList(int journalID);

    @Delete
    void deleteEntry(Entry entry);

    @Update
    void UpdateEntry(Entry entry);

    @Query("SELECT * from ENTRIES LIMIT 1")
    Entry[] getAnyEntry();

    @Query("SELECT * FROM ENTRIES WHERE content LIKE :searchText")
    public LiveData<List<Entry>> getDealsList(String searchText);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertEntryRX(Entry entry);
}
