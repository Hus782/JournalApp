package com.example.journal22;

import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.entity.Journal;
import com.example.journal22.data.entity.Template;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Utils {
    public static Entry createEntry(int entry_id, String title, String content, String date, long journalID) throws Exception {
        Entry entry = new Entry(title, content, date, journalID);
        return entry;
    }

    public static boolean compareEntries(Entry entry1, Entry entry2)  {
        assertEquals(entry1.getEntry_id(), entry2.getEntry_id());
        assertEquals(entry1.getTitle(), entry2.getTitle());
        assertEquals(entry1.getContent(), entry2.getContent());
        return true;
    }

    public static boolean compareTemps(Template a, Template b) throws Exception {
        assertThat(a.getTemplate_id(), is(b.getTemplate_id()));
        assertThat(a.getTitle(), is(b.getTitle()));
        assertThat(a.getContent(), is(b.getContent()));
        return true;
    }

    public static boolean compareJournals(Journal a, Journal b) throws Exception {
        assertThat(a.getJournal_id(), is(b.getJournal_id()));
        assertThat(a.getTitle(), is(b.getTitle()));
        return true;
    }
}
