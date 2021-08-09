package com.example.journal22;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;


import com.example.journal22.ui.entries.EntryViewModel;
import com.example.journal22.data.entity.Journal;
import com.example.journal22.data.JournalViewModel;
import com.example.journal22.ui.entries.EntryListAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity {//implements EntryListAdapter.ListItemClickListener {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //MyRecyclerViewAdapter adapter;
    private static final String TAG = "MyActivity";

    private EntryViewModel mWordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private EntryListAdapter adapter;


    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toogle;
    private NavigationView navView;
    private NavController navController;
    public List<Journal> journals;
    //public LiveData<Integer> currJournal;
    /*
    public MutableLiveData<Integer> currJournal =
            new MutableLiveData<>();
    */
    AppBarConfiguration mAppBarConfiguration;
    public int currJournalChecked = 1;
    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

/*
    public void start_entry(View view) {
        Intent intent = new Intent(this, calendar_view.class);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);

    }


    @Override
    public void onListItemClick(int position) {
      /*
        View v = recyclerView.findViewHolderForAdapterPosition(position).itemView;
        Log.v(TAG,  String.format("value = %d", position));
       // TextView txtId = v.findViewById(R.id.txtName);
        //String title = txtId.getText().toString();

       // Log.v(TAG,  title);

        TextView editText = v.findViewById(R.id.txtContent);
        String content = editText.getText().toString();
        TextView editText2 = v.findViewById(R.id.txtName);
        String title = editText2.getText().toString();
        Log.v(TAG, content);
        Log.v(TAG, title);

        Intent intent = new Intent(this, show_entry.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_TITLE",title);
        extras.putString("EXTRA_CONTENT",content);
        intent.putExtras(extras);
        startActivity(intent);

        */

   // }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        Toast.makeText(this, "CALLED ONCREATE here",
                Toast.LENGTH_SHORT).show();
        Log.v(TAG, String.valueOf("CALLED ONCREATE here"));
*/

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //myToolbar.showOverflowMenu();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_black_24dp);

/*
        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
               navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(
                myToolbar, navController, appBarConfiguration);
 */

        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        // Show and Manage the Drawer and Back Icon
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        navView = findViewById(R.id.nav_view);
        setMenu();
        NavigationUI.setupWithNavController(navView, navController);



  //      EntryViewModel mEntryViewModel = new ViewModelProvider(this).get((EntryViewModel.class));
        //final Menu menu = navView.getMenu();
/*
        this.currJournal.observe(this, id -> {
            mEntryViewModel.updateJournalID(id);
            Log.v("AAAAAAAAAAA", "Updated journalID!\n" );
          //  menu.findItem(id).setChecked(true);

        });

 */
        //setChecked();

        // Handle Navigation item clicks
        // This works with no further action on your part if the menu and destination id’s match.


    }

    public void setMenu() {
        JournalViewModel viewModel = new ViewModelProvider(this).get(JournalViewModel.class);
//            Journal journal = new Journal("Journal3");
//            viewModel.insert(journal);
        //EntryViewModel mEntryViewModel = new ViewModelProvider(this).get((EntryViewModel.class));
        EntryViewModel mEntryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(EntryViewModel.class);
        final Menu menu = navView.getMenu();
        viewModel.getAllJournals().observe(this, item -> {
            //List<Journal> AllJournals = item;
            //journals = item;

            int journSize = item.size();
            Log.v(TAG, String.valueOf(journSize));
            Log.v(TAG, "Updating Journals and items and stuff");

            //Toast.makeText(this, journSize, Toast.LENGTH_SHORT).show();

            menu.removeGroup(3);
            menu.add(3, R.id.all_journals,Menu.NONE,"All ").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mEntryViewModel.currJournal.setValue(-1);
                    return true;
                }
            });

            menu.add(3, R.id.new_journal,Menu.NONE,"Create Journal").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    setDialog();
                    return true;
                }
            });

            for (int i = 0; i < journSize; i++) {
                int id = item.get(i).getJournal_id();
                String title = item.get(i).getTitle();
                if(menu.findItem(id)==null){
                    menu.add(3, id,Menu.NONE,title).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        //menu.add(3, id,Menu.NONE,title).setCheckable(true).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Log.v(TAG, String.valueOf("Clicked "  + id));
                            SpannableString s = new SpannableString(title);
                            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            //currJournal.setValue(id);
                            // menu.findItem(id).setTitle(s);
                            menu.findItem(id).setChecked(true);
                            currJournalChecked = id;
                            //navView.setCheckedItem(id);
                            //      navController.navigate(R.id.action_main_fragment_self);
                            mEntryViewModel.currJournal.setValue(id);
                            drawerLayout.close();
                            return true;
                        }
                    });

                }
            }
        });
    }


        public void setDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Journal");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Add the buttons
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                JournalViewModel mJournalViewModel= new ViewModelProvider(MainActivity.this).get((JournalViewModel.class));
                String title = input.getText().toString();
                if(!title.isEmpty()){
                Journal journal = new Journal(title);
                mJournalViewModel.insert(journal);
                Toast.makeText(MainActivity.this, "Journal added!", Toast.LENGTH_SHORT).show();
                }
                else{ Toast.makeText(MainActivity.this, "Please add a name!", Toast.LENGTH_SHORT).show();

                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }


        @Override
    public boolean onSupportNavigateUp() {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation.
        return NavigationUI.navigateUp(navController, drawerLayout);
       // return NavigationUI.navigateUp(navController, drawerLayout);// || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // if(t.onOptionsItemSelected(item))
        //   return true;



        switch (item.getItemId()) {

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "Nothing happened!", Toast.LENGTH_SHORT).show();

                return true;
            // Android home
            //  case android.R.id.home:
            //     dl.openDrawer(GravityCompat.START);
            //    return true;
     /*
            case R.id.action_template:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "Templates showing!", Toast.LENGTH_SHORT).show();

                return true;
*/
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }


    }
        /*
        // drawer stuff
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        toogle = new ActionBarDrawerToggle(MainActivity.this, drawer,myToolbar,R.string.open, R.string.closed);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };


      //  mAppBarConfiguration = new AppBarConfiguration.Builder(
       //         R.id.main_fragment)
        //        .setDrawerLayout(dl)
           //     .build();


        drawer.addDrawerListener(toogle);
        //toogle.syncState();



        nv = (NavigationView)findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.item1:
                        Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();break;

                    default:
                        return true;
                }


                return true;

            }
        });


         */
/*
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
       NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
       NavigationUI.setupWithNavController(navView, navController);

*/


/*
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

       // Entry entry = new Entry(5,"Hello","Hello","2020/5/5");

        //planetArrayList.add(entry);
        //adapter = new PlanetAdapter(this, planetArrayList);
        adapter = new EntryListAdapter(new EntryListAdapter.WordDiff(),this);
        //final WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());

        recyclerView.setAdapter(adapter);

        //createListData();

        mWordViewModel = new ViewModelProvider(this).get((EntryViewModel.class));


        mWordViewModel.getAllWords().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(MainActivity.this, "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();


                TextView editText = view.findViewById(R.id.txtContent);
                String content = editText.getText().toString();
                TextView editText2 = view.findViewById(R.id.txtName);
                String title = editText2.getText().toString();
                Log.v(TAG, content);
                Log.v(TAG, title);

                Intent intent = new Intent(MainActivity.this, show_entry.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_TITLE",title);
                extras.putString("EXTRA_CONTENT",content);
                intent.putExtras(extras);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(MainActivity.this, "Long press on position :"+position,
                        //Toast.LENGTH_LONG).show();
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(item -> {
                    //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    //return true;
                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            Entry myWord = mWordViewModel.getEntry(position);
                            //Log.v("Got content mate", myWord.getContent());
                            mWordViewModel.deleteWord(myWord);
                            Toast.makeText(MainActivity.this, "Item deleted!", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.menu_nothing:
                            Toast.makeText(MainActivity.this, "Nothing happened!", Toast.LENGTH_SHORT).show();
                            return true;
                        default:
                            return false;
                    }
                });

                popup.show();//showing popup menu
            }




        }));


        //mWordViewModel.insert(entry);



/*
        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, animalNames);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }
    public void del(int position) {
        Entry entry = new Entry("Just Testin2","Just Testin2","2020/5/5");
        mWordViewModel.delete(entry);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "we in main activity");

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String title = extras.getString("EXTRA_TITLE");
            String content = extras.getString("EXTRA_CONTENT");
            String date = extras.getString("EXTRA_DATE");

            Log.v(TAG, "we in main activity again");

            Log.v(TAG, title);

            Log.v(TAG, content);

            Entry word = new Entry(title,content,date);
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Oops",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Log.v(TAG, "Clicked it maaaaaaaaaaaaaaaaaaaaate");

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    */
/*
    private void createListData() {
        Planet planet = new Planet("Title1", "ContentContent", 10, 12750);
        planetArrayList.add(planet);
        planet = new Planet("Title2", "ContentContent", 26, 143000);
        planetArrayList.add(planet);
        planet = new Planet("Title3", "ContentContent", 4, 6800);
        planetArrayList.add(planet);
        planet = new Planet("Title4", "ContentContent", 1, 2320);
        planetArrayList.add(planet);
        planet = new Planet("Title5", "ContentContent", 9, 12750);
        planetArrayList.add(planet);
        planet = new Planet("Title6", "ContentContent", 11, 120000);
        planetArrayList.add(planet);
        planet = new Planet("Title7", "ContentContent", 4, 4900);
        planetArrayList.add(planet);
        planet = new Planet("Title8", "ContentContent", 12, 50500);
        planetArrayList.add(planet);
        planet = new Planet("Title9", "ContentContent", 9, 52400);
        planetArrayList.add(planet);
        adapter.notifyDataSetChanged();
    }
*/

        //   public void onItemClick(View view, int position) {
        //       Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        //   }



/*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.main_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
*/

    /*
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //toogle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //toogle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);

        return true;
    }



     */



}