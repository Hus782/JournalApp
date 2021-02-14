package com.example.journal22.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEntry(Entry entry);

    @Query("Select * from ENTRIES")
    LiveData<List<Entry>> getEntryList();

    @Delete
    void deleteEntry(Entry entry);

    @Update
    void UpdateEntry(Entry entry);

    @Query("SELECT * from ENTRIES LIMIT 1")
    Entry[] getAnyEntry();

    @Query("SELECT * FROM ENTRIES WHERE content LIKE :searchText")
    public LiveData<List<Entry>> getDealsList(String searchText);

}
