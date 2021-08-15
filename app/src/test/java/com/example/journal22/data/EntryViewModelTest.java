package com.example.journal22.data;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.journal22.LiveDataTestUtil;
import com.example.journal22.Utils;
import com.example.journal22.UtilsMain;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.repository.EntryRepository;
import com.example.journal22.ui.entries.EntryViewModel;

import static com.example.journal22.data.TestData.ENTRY_EDITED;
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
public class EntryViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private EntryRepository mEntryRepository;

    private List<Entry> mAllEntriesList = new ArrayList<Entry>();
    private MutableLiveData<List<Entry>> mAllEntries = new MutableLiveData<List<Entry>>(mAllEntriesList);



    @Mock
    private Observer<List<Entry>> mockObserver;

    private EntryViewModel mEntryViewModel;


    public void initMock() {
        Mockito.doAnswer((Answer<Void>) invocation -> {
            mAllEntriesList.add(ENTRY_ENTITY);
            mAllEntries.postValue(mAllEntriesList);
            return null;
        }).when(mEntryRepository).insertWord(any());

        Mockito.doAnswer((Answer<Void>) invocation -> {
            mAllEntriesList.set(0,ENTRY_EDITED);
            mAllEntries.postValue(mAllEntriesList);
            return null;
        }).when(mEntryRepository).updateWord(any());

        Mockito.doAnswer((Answer<Void>) invocation -> {
            mAllEntriesList.remove(0);
            mAllEntries.postValue(mAllEntriesList);
            return null;
        }).when(mEntryRepository).deleteWord(any());


        Mockito.doAnswer(
                (Answer<LiveData<List<Entry>>>) invocation -> mAllEntries)
                .when(mEntryRepository).getAllEntries();


/*
        Mockito.when(mEntryRepository.getAllEntries()).thenAnswer((Answer<LiveData<List<Entry>>>) invocation -> {
        //    System.out.println("getAllEntries called mate\n");

            return mAllEntries;
        });

 */
    }

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mEntryViewModel = new EntryViewModel(
                mock(Application.class), mEntryRepository);
        initMock();
       // mEntryViewModel.getAllWords().observeForever(mockObserver);
        }


    @Test
    public void getEntriesTest() throws InterruptedException {
        List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryViewModel.getAllEntries());
        Mockito.verify(mEntryRepository, times(2)).getAllEntries();
        assertTrue(all_entries.isEmpty());

    }

    @Test
    public void testNull() {

        assertNotNull(mEntryViewModel.getAllEntries());
     //   assertTrue(mEntryViewModel.getAllWords().hasObservers());
     //   assertThat(mEntryViewModel.getAllWords().hasObservers(), is(true));


    }

    @Test
    public void insertEntryTest() throws Exception {
        mEntryViewModel.insertEntry(ENTRY_ENTITY);
        Mockito.verify(mEntryRepository).insertWord(ENTRY_ENTITY);
        List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryViewModel.getAllEntries());
        assertEquals(all_entries.size(), 1);
        assertTrue(Utils.compareEntries(all_entries.get(0), ENTRY_ENTITY));

        mEntryViewModel.insertEntry(ENTRY_ENTITY);
        all_entries = LiveDataTestUtil.getValue(mEntryViewModel.getAllEntries());
        assertEquals(all_entries.size(), 2);

    }

    @Test
    public void updateEntryTest() throws InterruptedException {
        mEntryViewModel.insertEntry(ENTRY_ENTITY);
        Mockito.verify(mEntryRepository).insertWord(ENTRY_ENTITY);
        mEntryViewModel.updateEntry(ENTRY_EDITED);
        Mockito.verify(mEntryRepository).updateWord(ENTRY_EDITED);

        List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryViewModel.getAllEntries());
        assertTrue(Utils.compareEntries(all_entries.get(0), ENTRY_EDITED));
        assertEquals(all_entries.size(), 1);

    }

    @Test
    public void deleteEntryTest() throws InterruptedException {
        mEntryViewModel.insertEntry(ENTRY_ENTITY);
        Mockito.verify(mEntryRepository).insertWord(ENTRY_ENTITY);
        mEntryViewModel.deleteEntry(ENTRY_ENTITY);
        Mockito.verify(mEntryRepository).deleteWord(ENTRY_ENTITY);
        List<Entry> all_entries = LiveDataTestUtil.getValue(mEntryViewModel.getAllEntries());

        assertTrue(all_entries.isEmpty());

    }

}