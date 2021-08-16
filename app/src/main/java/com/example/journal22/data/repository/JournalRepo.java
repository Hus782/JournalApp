package com.example.journal22.data.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.journal22.data.JournalAndEntries;
import com.example.journal22.data.MyDatabase;
import com.example.journal22.data.dao.JournalDao;
import com.example.journal22.data.entity.Journal;

import java.util.List;

public class JournalRepo {
    private MyDatabase mDatabase;
    private JournalDao mJournalDao;
    private LiveData<List<Journal>> mAllJournals;
    private static JournalRepo sInstance;

    public JournalRepo(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        mJournalDao = db.journalDao();
        mAllJournals = mJournalDao.getJournalList();
    }

    public JournalRepo(final MyDatabase database) {
        mDatabase = database;
        mJournalDao = mDatabase.journalDao();
        mAllJournals = mJournalDao.getJournalList();

    }
    public static JournalRepo getInstance(final MyDatabase database) {
        if (sInstance == null) {
            synchronized (EntryRepository.class) {
                if (sInstance == null) {
                    sInstance = new JournalRepo(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Journal>> getAllJournals() {
        return mAllJournals;
    }
    public LiveData<List<JournalAndEntries>> getAllJournalsAndEntries() {
        return mJournalDao.getJournalAndEntries();
    }


    public void insertJournal(Journal journal)  {
        new JournalRepo.insertJournalAsyncTask(mJournalDao).execute(journal);

    }

    public void deleteJournal(Journal journal)  {
        new JournalRepo.deleteJournalAsyncTask(mJournalDao).execute(journal);

    }


    public void updateJournal(Journal journal)  {
        new JournalRepo.updateJournalAsyncTask(mJournalDao).execute(journal);

    }

    private static class insertJournalAsyncTask extends AsyncTask<Journal, Void, Void> {
        private JournalDao mAsyncTaskDao;

        insertJournalAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Journal... params) {
            mAsyncTaskDao.createJournal(params[0]);
            return null;
        }
    }

    private static class deleteJournalAsyncTask extends AsyncTask<Journal, Void, Void> {
        private JournalDao mAsyncTaskDao;

        deleteJournalAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Journal... params) {
            mAsyncTaskDao.deleteJournal(params[0]);
            return null;
        }
    }

    private static class updateJournalAsyncTask extends AsyncTask<Journal, Void, Void> {
        private JournalDao mAsyncTaskDao;

        updateJournalAsyncTask(JournalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Journal... params) {
            mAsyncTaskDao.UpdateJournal(params[0]);
            return null;
        }
    }
}
