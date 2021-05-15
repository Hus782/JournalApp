package com.example.journal22.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class JournalRepo {
    private JournalDao mJournalDao;
    private LiveData<List<Journal>> mAllJournals;

    JournalRepo(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        mJournalDao = db.journalDao();
        mAllJournals = mJournalDao.getJournalList();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Journal>> getAllJournals() {
        return mAllJournals;
    }
    LiveData<List<JournalAndEntries>> getAllJournalsAndEntries() {
        return mJournalDao.getJournalAndEntries();
    }


    public void insertJournal(Journal journal)  {
        new JournalRepo.insertJournalAsyncTask(mJournalDao).execute(journal);
        Log.v("TAG", "Called del here");

    }

    public void deleteJournal(Journal journal)  {
        new JournalRepo.deleteJournalAsyncTask(mJournalDao).execute(journal);
        Log.v("TAG", "Called del here");

    }


    public void updateJournal(Journal journal)  {
        new JournalRepo.updateJournalAsyncTask(mJournalDao).execute(journal);
        Log.v("TAG", "Called del here");

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
