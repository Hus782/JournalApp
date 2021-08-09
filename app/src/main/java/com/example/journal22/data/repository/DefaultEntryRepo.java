package com.example.journal22.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.journal22.data.entity.Entry;

import java.util.List;

import io.reactivex.Completable;

public interface DefaultEntryRepo {
    LiveData<List<Entry>> getEntriesByID(int id);
    LiveData<List<Entry>> getAllEntries();

    Entry getWordAtPosition(int position);

    LiveData<List<Entry>> getFilteredEntries(String searchText);

    Completable insertEntryRX(Entry entry);

    void insertWord(Entry word);

    void deleteWord(Entry word);


    void updateWord(Entry word);
}
