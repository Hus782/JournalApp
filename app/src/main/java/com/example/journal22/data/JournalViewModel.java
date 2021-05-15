package com.example.journal22.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {
    private JournalRepo mRepository;

    private final LiveData<List<Journal>> mAllJournals;

    public JournalViewModel (Application application) {
        super(application);
        mRepository = new JournalRepo(application);
        mAllJournals = mRepository.getAllJournals();
    }

    public LiveData<List<Journal>> getAllJournals() { return mAllJournals; }
    public LiveData<List<JournalAndEntries>> getJournalsAndEntries() { return mRepository.getAllJournalsAndEntries(); }

    public void insert(Journal journal) { mRepository.insertJournal(journal); }
    public void delete(Journal journal) {mRepository.deleteJournal(journal);}
    public void update(Journal journal) {mRepository.updateJournal(journal);}
//
//    public Template getTemplate(int position) {return mRepository.getTemplateAtPosition(position);}

}
