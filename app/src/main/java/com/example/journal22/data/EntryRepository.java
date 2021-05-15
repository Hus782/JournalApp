package com.example.journal22.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;

public class EntryRepository {

    private EntryDao mEntryDao;
    private LiveData<List<Entry>> mAllEntries;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    EntryRepository(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        mEntryDao = db.myDao();
        mAllEntries = mEntryDao.getEntryList();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Entry>> getAllEntries() {
        return mAllEntries;
    }

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
    public Entry getWordAtPosition (int position) {
        return mAllEntries.getValue().get(position);
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
