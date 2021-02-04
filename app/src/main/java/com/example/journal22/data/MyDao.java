package com.example.journal22.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEntry(Entry entry);

    @Query("Select * from ENTRIES")
    LiveData<List<Entry>> getEntryList();

    @Delete
    void deleteEntry(Entry entry);

    @Query("SELECT * from ENTRIES LIMIT 1")
    Entry[] getAnyEntry();


}
