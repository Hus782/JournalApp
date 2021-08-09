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

public class EntryRepository implements DefaultEntryRepo{
    private MyDatabase db;
    private EntryDao mEntryDao;
    private LiveData<List<Entry>> mAllEntries;
   // private MyDatabase mDatabase;

    private static EntryRepository sInstance;

    //private int journalID;
    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

    public EntryRepository(Application application) {
        db = MyDatabase.getDatabase(application);
        mEntryDao = db.myDao();
        mAllEntries = mEntryDao.getEntryListByID(1);
        //Log.v("Repository fragment", String.valueOf("Contructor mAllEntries Updated\n\n"));

    }

    public EntryRepository(final MyDatabase database) {
        db = database;
        mEntryDao = db.myDao();

        mAllEntries = mEntryDao.getEntryListByID(1);

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








    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
  //  public LiveData<List<Entry>> getAllEntries() {
  //      return mAllEntries;
  //  }
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
/*
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Entry entry) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            mEntryDao.insertEntry(entry);
        });
    }

    void update(Entry entry) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            mEntryDao.insertEntry(entry);
        });
    }

    void delete(Entry entry) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            mEntryDao.deleteEntry(entry);
        });

    }

 */
    public Entry getWordAtPosition (int position) {
        return     mAllEntries.getValue().get(position);
    }

    public LiveData<List<Entry>> getFilteredEntries(String searchText){
        return mEntryDao.getDealsList(searchText);
    }

    public Completable insertEntryRX(Entry entry) {
        return null;
    }

    public void insertWord(Entry word)  {
        new insertEntryAsyncTask(mEntryDao).execute(word);
        Log.v("TAG", "Called del here");

    }

    public void deleteWord(Entry word)  {
        new deleteWordAsyncTask(mEntryDao).execute(word);
        Log.v("TAG", "Called del here");

    }


    public void updateWord(Entry word)  {
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




    /**
     *  Delete a single word from the database.
     */
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
