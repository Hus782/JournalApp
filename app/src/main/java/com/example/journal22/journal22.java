package com.example.journal22;

import android.app.Application;

import com.example.journal22.data.repository.EntryRepository;
import com.example.journal22.data.MyDatabase;

public class journal22 extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public MyDatabase getDatabase() {
        return MyDatabase.getDatabase(this);
    }

    public EntryRepository getRepository() {
        return EntryRepository.getInstance(getDatabase());
    }
}
