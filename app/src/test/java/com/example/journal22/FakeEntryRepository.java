package com.example.journal22;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.repository.DefaultEntryRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;

public class FakeEntryRepository implements DefaultEntryRepo {
    private MutableLiveData<List<Entry>> mAllEntries = new MutableLiveData<List<Entry>>();

    @Override
    public LiveData<List<Entry>> getEntriesByID(int id) {
        List<Entry> temp = new ArrayList<>();
        for (Entry i : mAllEntries.getValue()) {
            if (i.getJournalID() == id){
                temp.add(i);
            }
        }
        MutableLiveData<List<Entry>> entriesByID = new MutableLiveData<List<Entry>>();
        entriesByID.setValue(temp);
        return entriesByID;
    }

    @Override
    public LiveData<List<Entry>> getAllEntries() {

        return mAllEntries;
    }

    @Override
    public Entry getWordAtPosition(int position) {
        return null;
    }

    @Override
    public LiveData<List<Entry>> getFilteredEntries(String searchText) {
        return null;
    }

    @Override
    public Completable insertEntryRX(Entry entry) {
        return null;
    }

    @Override
    public void insertWord(Entry word) {
        List<Entry> temp = mAllEntries.getValue();
        temp.add(word);
        mAllEntries.setValue(temp);
    }

    @Override
    public void deleteWord(Entry word) {
        List<Entry> temp = mAllEntries.getValue();
        temp.remove(word);
        mAllEntries.setValue(temp);
    }

    @Override
    public void updateWord(Entry word) {
        List<Entry> temp = mAllEntries.getValue();
        temp.set(0,word);
        mAllEntries.setValue(temp);
    }
}
