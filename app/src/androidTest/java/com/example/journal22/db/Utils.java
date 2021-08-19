package com.example.journal22.db;

import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.entity.Journal;
import com.example.journal22.data.entity.Template;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Utils {
    /*public static Entry createEntry(int entry_id, String title, String content, String date, long journalID) throws Exception {
        Entry entry = new Entry(title, content, date, journalID);
        return entry;
    }


     */
    public static boolean compareEntries(Entry entry1, Entry entry2) throws Exception {
        assertThat(entry1.getEntry_id(), is(entry2.getEntry_id()));
        assertThat(entry1.getTitle(), is(entry2.getTitle()));
        assertThat(entry1.getContent(), is(entry2.getContent()));
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



    public static long countWords(String string) {
        String trim = string.trim();
        if (trim.isEmpty())
            return 0;
        return trim.split("\\s+").length; // separate string around spaces
    }
}
