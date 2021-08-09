package com.example.journal22.ui.entries;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.repository.DefaultEntryRepo;
import com.example.journal22.data.repository.EntryRepository;
import com.example.journal22.journal22;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private DefaultEntryRepo mRepository;
    private LiveData<List<Entry>> mAllEntries;
    public MutableLiveData<Integer> currJournal =
            new MutableLiveData<>();

    public EntryViewModel (Application application) {
        super(application);
        mRepository = ((journal22) application).getRepository();
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

    public void insertEntry(Entry entry) { mRepository.insertWord(entry); }
    public void deleteEntry(Entry entry) {mRepository.deleteWord(entry);}
    public void updateEntry(Entry entry) {mRepository.updateWord(entry);}
    public Entry getEntry(int position) {return mRepository.getWordAtPosition(position);}

    /*
    public int getSize() {return mAllEntries.getValue().size();}
    public void delete(Entry entry) { mRepository.delete(entry); }
    public LiveData<List<Entry>> getFilteredEntries(String searchText){
        return mRepository.getFilteredEntries(searchText);
    }
*/

}
