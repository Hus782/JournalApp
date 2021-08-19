package com.example.journal22.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.journal22.data.entity.Template;

import java.util.List;

@Dao
public interface TemplateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTemplate(Template template);

    @Query("Select * from templates")
    LiveData<List<Template>> getTemplateList();

    @Delete
    void deleteTemplate(Template template);

    @Update
    void UpdateTemplate(Template template);
}
