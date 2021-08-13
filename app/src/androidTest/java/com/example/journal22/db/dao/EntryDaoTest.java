package com.example.journal22.db.dao;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.journal22.data.MyDatabase;
import com.example.journal22.data.dao.EntryDao;
import com.example.journal22.data.entity.Entry;
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
import static com.example.journal22.db.dao.TestData.ENTRY_ENTITY2;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EntryDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MyDatabase mDatabase;

    private EntryDao mEntryDao;


    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                MyDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        mEntryDao = mDatabase.myDao();
    }
    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getEntriesBeforeInsert() throws InterruptedException {
        List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryDao.getEntryListAll());

        assertTrue(all_entries.isEmpty());
    }

    @Test
    public void insertTest1() throws Exception {

        mEntryDao.insertEntry(ENTRY_ENTITY);

        List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryDao.getEntryListAll());

        assertThat(Utils.compareEntries(all_entries.get(0), ENTRY_ENTITY), is(true));
        assertEquals(all_entries.get(0).getWordsCount(), Utils.countWords(all_entries.get(0).getContent()));
    }
    @Test
    public void insertTest2() throws Exception {
        mEntryDao.insertEntry(ENTRY_ENTITY);
        mEntryDao.insertEntry(ENTRY_ENTITY2);
        List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryDao.getEntryListAll());
        assertThat(Utils.compareEntries(all_entries.get(1), ENTRY_ENTITY2), is(true));

    }
    @Test
    public void updateTest() throws Exception {
        mEntryDao.insertEntry(ENTRY_ENTITY);
        mEntryDao.UpdateEntry(ENTRY_EDITED);
        List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryDao.getEntryListAll());
        assertThat(Utils.compareEntries(all_entries.get(0), ENTRY_EDITED), is(true));

    }

    @Test
    public void deleteTest() throws Exception {
        mEntryDao.insertEntry(ENTRY_ENTITY);
        mEntryDao.deleteEntry(ENTRY_ENTITY);
        List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryDao.getEntryListAll());
        assertThat(all_entries.isEmpty(), is(true));

    }
/*
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.journal22", appContext.getPackageName());
    }
    */
}