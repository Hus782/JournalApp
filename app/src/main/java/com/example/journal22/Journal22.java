package com.example.journal22;

import android.app.Application;

import com.example.journal22.data.repository.EntryRepository;
import com.example.journal22.data.MyDatabase;
import com.example.journal22.data.repository.JournalRepo;
import com.example.journal22.data.repository.TemplateRepo;

public class Journal22 extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public MyDatabase getDatabase() {
        return MyDatabase.getDatabase(this);
    }

    public EntryRepository getEntryRepository() {
        return EntryRepository.getInstance(getDatabase());
    }

    public JournalRepo getJournalRepository() {
        return JournalRepo.getInstance(getDatabase());
    }

    public TemplateRepo getTemplateRepository() {
        return TemplateRepo.getInstance(getDatabase());
    }
}
