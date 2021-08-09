package com.example.journal22.db.dao;

import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.entity.Journal;
import com.example.journal22.data.entity.Template;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestData {

    static final Entry ENTRY_ENTITY = new Entry(1,"title1", "content1", "893024809",
            1);
    static final Entry ENTRY_ENTITY2 = new Entry(2,"title2", "content2", "893024809",
            1);
    static final Entry ENTRY_ENTITY3 = new Entry(3,"title3", "content3", "893024809",
            2);
    static final Entry ENTRY_EDITED = new Entry(1,"edited_title", "edited content", "893024809",
            1);
    static final List<Entry> ENTRIES = Arrays.asList(ENTRY_ENTITY, ENTRY_ENTITY2);

    static final Template TEMPLATE_ENTITY = new Template(1,"title","Content");
    static final Template TEMPLATE_EDITED = new Template(1,"title_edited","Content_maybe_edited");

    static final Journal JOURNAL_ENTITY = new Journal(1,"title");
    static final Journal JOURNAL_ENTITY2 = new Journal(2,"title");

    static final Journal JOURNAL_EDITED = new Journal(1,"title_edited");

}