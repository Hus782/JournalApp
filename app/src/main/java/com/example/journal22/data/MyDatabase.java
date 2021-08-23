package com.example.journal22.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.journal22.data.dao.EntryDao;
import com.example.journal22.data.dao.JournalDao;
import com.example.journal22.data.dao.TemplateDao;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.entity.Journal;
import com.example.journal22.data.entity.Template;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Entry.class, Template.class, Journal.class}, version = 3,  exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
        public abstract EntryDao entryDao();
    public abstract TemplateDao templateDao();
    public abstract JournalDao journalDao();

    private static volatile MyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
//"dd-MMMM-yyyy-EEEE";
    static final Entry ENTRY1 = new Entry(1,"title1", "content1 ct2 ct3 ct4 ct5 ct6 ct7 ct8", "21-August-2021","21:00",
            1, 8);
    static final Entry ENTRY2 = new Entry(1,"title1", "content1 ct2 ct3 ct4 ct5 ct6 ct7 ct8", "20-August-2021","21:00",
            1, 8);
    static final Entry ENTRY3 = new Entry(1,"title1", "content1 ct2 ct3 ct4 ct5 ct6 ct7 ct8", "19-August-2021","21:00",
            1, 8);

    public static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "entries_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
//                            .allowMainThreadQueries()

                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                EntryDao dao = INSTANCE.entryDao();
                dao.insertEntry(ENTRY1);
                dao.insertEntry(ENTRY2);
                dao.insertEntry(ENTRY3);

                //  TemplateDao tempDao = INSTANCE.templateDao();
        //        JournalDao journalDao = INSTANCE.journalDao();

              //  Journal journal = new Journal("Journal1");
              //  journalDao.createJournal(journal);
               // Entry entry = new Entry("Just Testin","Just Testin","2020/5/5");
               // dao.insertEntry(entry);

               // Template temp = new Template("Just Testin","Just Testin");
               // tempDao.insertTemplate(temp);
            });
        }
    };

}
