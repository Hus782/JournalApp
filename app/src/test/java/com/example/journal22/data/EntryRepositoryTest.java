package com.example.journal22.data;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.journal22.data.dao.EntryDao;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.repository.EntryRepository;

import static com.example.journal22.data.TestData.ENTRY_ENTITY;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

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

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class EntryRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private EntryDao mEntryDao;

    @Mock
    private MyDatabase myDatabase;


    private EntryRepository mEntryRepository;
    private List<Entry> mAllEntriesList = new ArrayList<Entry>();
    private MutableLiveData<List<Entry>> mAllEntries = new MutableLiveData<List<Entry>>(mAllEntriesList);


    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mEntryRepository = new EntryRepository(mEntryDao);
        initMock();
        // mEntryViewModel.getAllWords().observeForever(mockObserver);
    }

    public void initMock() {
        Mockito.doAnswer((Answer<Void>) invocation -> {
            mAllEntriesList.add(ENTRY_ENTITY);
            mAllEntries.postValue(mAllEntriesList);
            return null;
        }).when(mEntryDao).insertEntry(any());

        Mockito.doAnswer(
                (Answer<LiveData<List<Entry>>>) invocation -> mAllEntries)
                .when(mEntryDao).getEntryListAll();
    }



    @Test
    public void insertTest()  {
       // List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryViewModel.getAllEntries());
       // Mockito.verify(mEntryRepository, times(2)).getAllEntries();
        mEntryRepository.insertEntryTask(ENTRY_ENTITY);
      assertEquals(mEntryDao, null);

    }

}
