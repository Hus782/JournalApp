package com.example.journal22.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.journal22.Journal22;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.entity.Journal;
import com.example.journal22.data.repository.EntryRepository;
import com.example.journal22.data.repository.JournalRepo;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {
    private JournalRepo mJournalRepository;
    private EntryRepository mEntryRepository;

    private final LiveData<List<Journal>> mAllJournals;

    public JournalViewModel (Application application) {
        super(application);
        //mJournalRepository = new JournalRepo(application);
        mJournalRepository = ((Journal22) application).getJournalRepository();
        mAllJournals = mJournalRepository.getAllJournals();
        mEntryRepository = new EntryRepository(application);
    }

    public LiveData<List<Journal>> getAllJournals() { return mAllJournals; }
    public LiveData<List<JournalAndEntries>> getJournalsAndEntries() { return mJournalRepository.getAllJournalsAndEntries(); }

    public void insert(Journal journal) { mJournalRepository.insertJournal(journal); }
    public void delete(Journal journal) {mJournalRepository.deleteJournal(journal);}
    public void update(Journal journal) {mJournalRepository.updateJournal(journal);}
    //public Entry getEntry(int position) {return mEntryRepository.getEntryAtPosition(position);}

//
//    public Template getTemplate(int position) {return mRepository.getTemplateAtPosition(position);}

}
