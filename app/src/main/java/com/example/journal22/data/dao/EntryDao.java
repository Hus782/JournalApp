package com.example.journal22.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.journal22.data.entity.Entry;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEntry(Entry entry);

    @Query("Select * from ENTRIES where journalID = :journalID")
    LiveData<List<Entry>> getEntryListByID(int journalID);

    @Query("Select * from ENTRIES")
    LiveData<List<Entry>> getEntryListAll();

    @Delete
    void deleteEntry(Entry entry);

    @Update
    void UpdateEntry(Entry entry);

    @Query("SELECT * from ENTRIES LIMIT 1")
    Entry[] getAnyEntry();

    @Query("SELECT * FROM ENTRIES WHERE content LIKE :searchText")
    LiveData<List<Entry>> getDealsList(String searchText);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertEntryRX(Entry entry);
}
