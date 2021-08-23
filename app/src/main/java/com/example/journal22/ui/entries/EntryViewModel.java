package com.example.journal22.ui.entries;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.repository.EntryRepository;
import com.example.journal22.Journal22;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private EntryRepository mRepository;
    private LiveData<List<Entry>> mAllEntries;
    private MutableLiveData<Integer> currJournal =
            new MutableLiveData<>();

    public EntryViewModel (Application application) {
        super(application);
        mRepository = ((Journal22) application).getEntryRepository();
        //mRepository = new EntryRepository(application);

        mAllEntries = mRepository.getAllEntries();
        currJournal.setValue(-1);
    }

    public EntryViewModel (Application application, EntryRepository repository) {
        super(application);
        mRepository = repository;
        //mRepository = new EntryRepository(application);

        mAllEntries = mRepository.getAllEntries();
        currJournal.setValue(-1);
    }

    public LiveData<List<Entry>> getAllEntries() {

        return Transformations.switchMap(currJournal, id ->
               id == -1? mRepository.getAllEntries()  : mRepository.getEntriesByID(id));
    }

    public void insertEntry(Entry entry) { mRepository.insertEntryTask(entry); }
    public void deleteEntry(Entry entry) {mRepository.deleteEntryTask(entry);}
    public void updateEntry(Entry entry) {mRepository.updateEntryTask(entry);}
    public Entry getEntry(int position) {return mRepository.getEntryAtPosition(position);}
    public LiveData<List<Entry>> getFilteredEntries(String searchText){
        String wildCard = '%' + searchText + '%';
        return mRepository.getFilteredEntries(wildCard);
    }
    public LiveData<List<String>> getDays() { return mRepository.getEntryDays(); }


    public MutableLiveData<Integer> getCurrJournal() { return currJournal; }
    public void setCurrJournal(int currJournal) {  this.currJournal.setValue(currJournal); }

}
