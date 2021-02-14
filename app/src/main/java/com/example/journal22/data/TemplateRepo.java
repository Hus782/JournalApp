package com.example.journal22.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TemplateRepo {
    private TemplateDao mTempDao;
    private LiveData<List<Template>> mAllTemplates;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    TemplateRepo(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        mTempDao = db.templateDao();
        mAllTemplates = mTempDao.getTemplateList();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Template>> getAllTemplates() {
        return mAllTemplates;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Template template) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            mTempDao.insertTemplate(template);
        });
    }
    public Template getTemplateAtPosition (int position) {
        return mAllTemplates.getValue().get(position);
    }



    public void deleteWord(Template word)  {
        new TemplateRepo.deleteTempAsyncTask(mTempDao).execute(word);
        Log.v("TAG", "Called del here");

    }


    public void updateWord(Template word)  {
        new TemplateRepo.updateTempAsyncTask(mTempDao).execute(word);
        Log.v("TAG", "Called del here");

    }

    private static class deleteTempAsyncTask extends AsyncTask<Template, Void, Void> {
        private TemplateDao mAsyncTaskDao;

        deleteTempAsyncTask(TemplateDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Template... params) {
            mAsyncTaskDao.deleteTemplate(params[0]);
            return null;
        }
    }

    private static class updateTempAsyncTask extends AsyncTask<Template, Void, Void> {
        private TemplateDao mAsyncTaskDao;

        updateTempAsyncTask(TemplateDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Template... params) {
            mAsyncTaskDao.UpdateTemplate(params[0]);
            return null;
        }
    }
/*
    void delete(Entry entry) {
        MyDatabase.databaseWriteExecutor.execute(() -> {
            mEntryDao.deleteEntry(entry);
        });

    }
    public Entry getWordAtPosition (int position) {
        return mAllEntries.getValue().get(position);
    }



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
*/
}
