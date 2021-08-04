package com.example.journal22.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {
    private JournalRepo mJournalRepository;
    private EntryRepository mEntryRepository;

    private final LiveData<List<Journal>> mAllJournals;
    private final LiveData<List<Entry>> mAllEntries;

    public JournalViewModel (Application application) {
        super(application);
        mJournalRepository = new JournalRepo(application);
        mAllJournals = mJournalRepository.getAllJournals();
        mEntryRepository = new EntryRepository(application);
        mAllEntries = mEntryRepository.getAllEntries();
    }

    public LiveData<List<Journal>> getAllJournals() { return mAllJournals; }
    public LiveData<List<JournalAndEntries>> getJournalsAndEntries() { return mJournalRepository.getAllJournalsAndEntries(); }

    public void insert(Journal journal) { mJournalRepository.insertJournal(journal); }
    public void delete(Journal journal) {mJournalRepository.deleteJournal(journal);}
    public void update(Journal journal) {mJournalRepository.updateJournal(journal);}
    public Entry getEntry(int position) {return mEntryRepository.getWordAtPosition(position);}

//
//    public Template getTemplate(int position) {return mRepository.getTemplateAtPosition(position);}

}
