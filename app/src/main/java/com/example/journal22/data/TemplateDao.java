package com.example.journal22.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TemplateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTemplate(Template template);

    @Query("Select * from templates")
    LiveData<List<Template>> getTemplateList();

    @Delete
    void deleteTemplate(Template template);

}
