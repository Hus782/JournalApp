package com.example.journal22.db.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.journal22.data.JournalCount;
import com.example.journal22.data.MyDatabase;
import com.example.journal22.data.dao.EntryDao;
import com.example.journal22.data.dao.JournalDao;
import com.example.journal22.data.entity.Journal;
import com.example.journal22.db.LiveDataTestUtil;
import com.example.journal22.db.Utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.example.journal22.db.dao.TestData.*;
import static com.example.journal22.db.dao.TestData.JOURNAL_EDITED;
import static com.example.journal22.db.dao.TestData.JOURNAL_ENTITY;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)

public class JournalDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MyDatabase mDatabase;

    private JournalDao mJournalDao;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                MyDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        mJournalDao = mDatabase.journalDao();
    }
    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getJournalsBeforeInsert() throws InterruptedException {
        List<Journal> all_journals = LiveDataTestUtil.getValue(mJournalDao.getJournalList());
        assertTrue(all_journals.isEmpty());
    }

    @Test
    public void insertTest1() throws Exception {
        mJournalDao.createJournal(JOURNAL_ENTITY);
        List<Journal> all_journals = LiveDataTestUtil.getValue(mJournalDao.getJournalList());
        assertThat(Utils.compareJournals(all_journals.get(0), JOURNAL_ENTITY), is(true));

    }

    @Test
    public void updateTest() throws Exception {
        mJournalDao.createJournal(JOURNAL_ENTITY);
        mJournalDao.UpdateJournal(JOURNAL_EDITED);
        List<Journal> all_journals = LiveDataTestUtil.getValue(mJournalDao.getJournalList());
        assertThat(Utils.compareJournals(all_journals.get(0), JOURNAL_EDITED), is(true));

    }

    @Test
    public void deleteTest() throws Exception {
        mJournalDao.createJournal(JOURNAL_ENTITY);
        mJournalDao.deleteJournal(JOURNAL_ENTITY);
        List<Journal> all_journals = LiveDataTestUtil.getValue(mJournalDao.getJournalList());
        assertThat(all_journals.isEmpty(), is(true));

    }

    @Test
    public void getEntriesCountTest() throws Exception {
        mJournalDao.createJournal(JOURNAL_ENTITY);
        mJournalDao.createJournal(JOURNAL_ENTITY2);

        EntryDao mEntryDao = mDatabase.entryDao();
        mEntryDao.insertEntry(ENTRY_ENTITY);
        mEntryDao.insertEntry(ENTRY_ENTITY3);

        List<JournalCount> all_journals = LiveDataTestUtil.getValue(mJournalDao.getCount());
        assertNotNull(all_journals);
        Assert.assertEquals(String.valueOf(all_journals.get(0).entries_count), String.valueOf(1));
        assertThat(Utils.compareJournals(all_journals.get(0).journal, JOURNAL_ENTITY), is(true));
        Assert.assertEquals(String.valueOf(all_journals.get(1).entries_count), String.valueOf(1));
        assertThat(Utils.compareJournals(all_journals.get(1).journal, JOURNAL_ENTITY2), is(true));

        //assertThat(Utils.compareJournals(all_journals.get(0).Journal, JOURNAL_ENTITY), is(true));
        //Assert.assertEquals(String.valueOf(all_journals.get(0).entries.size()), String.valueOf(1);


    }
}
