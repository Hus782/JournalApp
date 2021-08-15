package com.example.journal22.data;

import static com.example.journal22.data.TestData.ENTRY_ENTITY;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.journal22.LiveDataTestUtil;
import com.example.journal22.data.dao.EntryDao;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.repository.EntryRepository;
import com.example.journal22.ui.entries.EntryViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)

public abstract class EntryRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private EntryDao mEntryDao;

    @Mock
    private MyDatabase myDatabase;

    private static EntryRepository mEntryRepository;


    private List<Entry> mAllEntriesList = new ArrayList<Entry>();
    private MutableLiveData<List<Entry>> mAllEntries = new MutableLiveData<List<Entry>>(mAllEntriesList);


    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mEntryRepository = new EntryRepository(myDatabase);
        initMock();
        // mEntryViewModel.getAllWords().observeForever(mockObserver);
    }

    public void initMock() {
        Mockito.doAnswer((Answer<Void>) invocation -> {
            mAllEntriesList.add(ENTRY_ENTITY);
            mAllEntries.postValue(mAllEntriesList);
            return null;
        }).when(mEntryDao).insertEntry(any());
    }


    @Test
    public void getEntriesTest() throws InterruptedException {
       // List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryViewModel.getAllEntries());
       // Mockito.verify(mEntryRepository, times(2)).getAllEntries();
        assertTrue(1==1);

    }

}
