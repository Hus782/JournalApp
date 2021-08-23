package com.example.journal22.data.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.journal22.data.MyDatabase;
import com.example.journal22.data.dao.EntryDao;
import com.example.journal22.data.entity.Entry;

import java.util.List;

import io.reactivex.Completable;

public class EntryRepository{
    private MyDatabase mDatabase;
    private EntryDao mEntryDao;
    private LiveData<List<Entry>> mAllEntries;

    private static EntryRepository sInstance;


    public EntryRepository(Application application) {
        mDatabase = MyDatabase.getDatabase(application);
        mEntryDao = mDatabase.entryDao();
        mAllEntries = mEntryDao.getEntryListAll();

    }

    public EntryRepository(final MyDatabase database) {
        mDatabase = database;
        mEntryDao = mDatabase.entryDao();
        mAllEntries = mEntryDao.getEntryListAll();

    }
    public EntryRepository(final EntryDao dao) {
        mEntryDao = dao;

        mAllEntries = mEntryDao.getEntryListAll();

    }

    public static EntryRepository getInstance(final MyDatabase database) {
        if (sInstance == null) {
            synchronized (EntryRepository.class) {
                if (sInstance == null) {
                    sInstance = new EntryRepository(database);
                }
            }
        }
        return sInstance;
    }


    public LiveData<List<Entry>> getEntriesByID(int id) {
       // mAllEntries = mEntryDao.getEntryList(journalID);
        mAllEntries = mEntryDao.getEntryListByID(id);

        return mAllEntries;
    }
    public LiveData<List<Entry>> getAllEntries() {
        // mAllEntries = mEntryDao.getEntryList(journalID);
        mAllEntries = mEntryDao.getEntryListAll();

        return mAllEntries;
    }

    public Entry getEntryAtPosition(int position) {
        return     mAllEntries.getValue().get(position);
    }

    public LiveData<List<String>> getEntryDays() {
        return mEntryDao.getAllEntryDays();
    }

    public LiveData<List<Entry>> getFilteredEntries(String searchText){
        return mEntryDao.getDealsList(searchText);
    }

    public Completable insertEntryRX(Entry entry) {
        return null;
    }

    public void insertEntryTask(Entry word)  {
        new insertEntryAsyncTask(mEntryDao).execute(word);
        Log.v("TAG", "Called del here");

    }

    public void deleteEntryTask(Entry word)  {
        new deleteWordAsyncTask(mEntryDao).execute(word);
        Log.v("TAG", "Called del here");

    }


    public void updateEntryTask(Entry word)  {
        new updateWordAsyncTask(mEntryDao).execute(word);
        Log.v("TAG", "Called del here");

    }


    private static class insertEntryAsyncTask extends AsyncTask<Entry, Void, Void> {
        private EntryDao mAsyncTaskDao;

        insertEntryAsyncTask(EntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.insertEntry(params[0]);
            return null;
        }
    }




    private static class deleteWordAsyncTask extends AsyncTask<Entry, Void, Void> {
        private EntryDao mAsyncTaskDao;

        deleteWordAsyncTask(EntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.deleteEntry(params[0]);
            return null;
        }
    }

    private static class updateWordAsyncTask extends AsyncTask<Entry, Void, Void> {
        private EntryDao mAsyncTaskDao;

        updateWordAsyncTask(EntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.UpdateEntry(params[0]);
            return null;
        }
    }

}
