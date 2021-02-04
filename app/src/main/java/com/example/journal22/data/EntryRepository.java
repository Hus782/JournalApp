package com.example.journal22.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.journal22.MainActivity;

import java.util.List;

public class EntryRepository {

    private MyDao mEntryDao;
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

    void delete(Entry entry) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            mEntryDao.deleteEntry(entry);
        });

    }
    public Entry getWordAtPosition (int position) {
        return mAllEntries.getValue().get(position);
    }


    public void deleteWord(Entry word)  {
        new deleteWordAsyncTask(mEntryDao).execute(word);
        Log.v("TAG", "Called del here");

    }
    /**
     *  Delete a single word from the database.
     */
    private static class deleteWordAsyncTask extends AsyncTask<Entry, Void, Void> {
        private MyDao mAsyncTaskDao;

        deleteWordAsyncTask(MyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.deleteEntry(params[0]);
            return null;
        }
    }

}
