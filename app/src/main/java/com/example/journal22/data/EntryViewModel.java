package com.example.journal22.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private EntryRepository mRepository;
    private LiveData<List<Entry>> mAllEntries;
    private int journalID;

    public EntryViewModel (Application application) {
        super(application);
        mRepository = new EntryRepository(application);
        mAllEntries = mRepository.getAllEntries();
    }

    public LiveData<List<Entry>> getAllWords() {
        //mAllEntries = mRepository.getAllEntries(journalID);
        return mAllEntries; }

    public void insert(Entry entry) { mRepository.insertWord(entry); }
   // public void delete(Entry entry) { mRepository.delete(entry); }
    public void deleteWord(Entry entry) {mRepository.deleteWord(entry);}
    public void updateEntry(Entry entry) {mRepository.updateWord(entry);}
    public int getSize() {return mAllEntries.getValue().size();}
    public void updateJournalID(int newJournalID) { mRepository.updateJournalID(newJournalID);
        mAllEntries = mRepository.getAllEntries();
        
    }
    public Entry getEntry(int position) {return mRepository.getWordAtPosition(position);}

    public LiveData<List<Entry>> getFilteredEntries(String searchText){
        return mRepository.getFilteredEntries(searchText);
    }
}
