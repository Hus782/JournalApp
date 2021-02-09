package com.example.journal22.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private EntryRepository mRepository;

    private final LiveData<List<Entry>> mAllEntries;

    public EntryViewModel (Application application) {
        super(application);
        mRepository = new EntryRepository(application);
        mAllEntries = mRepository.getAllEntries();
    }

    public LiveData<List<Entry>> getAllWords() { return mAllEntries; }

    public void insert(Entry entry) { mRepository.insert(entry); }
   // public void delete(Entry entry) { mRepository.delete(entry); }
    public void deleteWord(Entry entry) {mRepository.deleteWord(entry);}
    public void updateEntry(Entry entry) {mRepository.updateWord(entry);}

    public Entry getEntry(int position) {return mRepository.getWordAtPosition(position);}


}
