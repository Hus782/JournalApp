package com.example.journal22.data.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.journal22.data.MyDatabase;
import com.example.journal22.data.dao.TemplateDao;
import com.example.journal22.data.entity.Template;

import java.util.List;

public class TemplateRepo {
    private MyDatabase mDatabase;
    private TemplateDao mTempDao;
    private LiveData<List<Template>> mAllTemplates;
    private static TemplateRepo sInstance;

    public TemplateRepo(Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        mTempDao = db.templateDao();
        mAllTemplates = mTempDao.getTemplateList();
    }

    public TemplateRepo(final MyDatabase database) {
        mDatabase = database;
        mTempDao = mDatabase.templateDao();
        mAllTemplates = mTempDao.getTemplateList();
    }

    public static TemplateRepo getInstance(final MyDatabase database) {
        if (sInstance == null) {
            synchronized (EntryRepository.class) {
                if (sInstance == null) {
                    sInstance = new TemplateRepo(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<Template>> getAllTemplates() {
        return mAllTemplates;
    }

    public Template getTemplateAtPosition (int position) {
        return mAllTemplates.getValue().get(position);
    }



    public void insertWord(Template temp)  {
        new TemplateRepo.insertTempAsyncTask(mTempDao).execute(temp);
    }

    public void deleteWord(Template temp)  {
        new TemplateRepo.deleteTempAsyncTask(mTempDao).execute(temp);
    }


    public void updateWord(Template temp)  {
        new TemplateRepo.updateTempAsyncTask(mTempDao).execute(temp);
    }


    private static class insertTempAsyncTask extends AsyncTask<Template, Void, Void> {
        private TemplateDao mAsyncTaskDao;

        insertTempAsyncTask(TemplateDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Template... params) {
            mAsyncTaskDao.insertTemplate(params[0]);
            return null;
        }
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
}
