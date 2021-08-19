package com.example.journal22.db.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.journal22.data.MyDatabase;
import com.example.journal22.data.dao.EntryDao;
import com.example.journal22.data.dao.TemplateDao;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.entity.Template;
import com.example.journal22.db.LiveDataTestUtil;
import com.example.journal22.db.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.example.journal22.db.dao.TestData.ENTRY_EDITED;
import static com.example.journal22.db.dao.TestData.ENTRY_ENTITY;
import static com.example.journal22.db.dao.TestData.TEMPLATE_EDITED;
import static com.example.journal22.db.dao.TestData.TEMPLATE_ENTITY;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class TemplateDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MyDatabase mDatabase;

    private TemplateDao mTemplateDao;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                MyDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        mTemplateDao = mDatabase.templateDao();
    }
    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getTempsBeforeInsert() throws InterruptedException {
        List<Template> all_temps = LiveDataTestUtil.getValue(mTemplateDao.getTemplateList());
        assertTrue(all_temps.isEmpty());
    }


    @Test
    public void insertTemplate() throws Exception {

        mTemplateDao.insertTemplate(TEMPLATE_ENTITY);

        List<Template> all_temps = LiveDataTestUtil.getValue(mTemplateDao.getTemplateList());

        assertThat(Utils.compareTemps(all_temps.get(0), TEMPLATE_ENTITY), is(true));

    }


    @Test
    public void deleteTemplate() throws InterruptedException {
        mTemplateDao.insertTemplate(TEMPLATE_ENTITY);
        mTemplateDao.deleteTemplate(TEMPLATE_ENTITY);
        List<Template> all_temps = LiveDataTestUtil.getValue(mTemplateDao.getTemplateList());
        assertThat(all_temps.isEmpty(), is(true));
    }

    @Test
    public void updateTemplate() throws Exception {
        mTemplateDao.insertTemplate(TEMPLATE_ENTITY);
        mTemplateDao.UpdateTemplate(TEMPLATE_EDITED);
        List<Template> all_temps = LiveDataTestUtil.getValue(mTemplateDao.getTemplateList());
        assertThat(Utils.compareTemps(all_temps.get(0), TEMPLATE_EDITED), is(true));
    }
}