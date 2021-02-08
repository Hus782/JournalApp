package com.example.journal22;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.journal22.data.Entry;
import com.example.journal22.data.EntryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;





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


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.showOverflowMenu();
        getSupportActionBar().setTitle("title");

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.main_fragment).build();
        NavController navController = Navigation.findNavController(this, R.id.main_nav_host_fragment);

        NavigationUI.setupWithNavController(
                myToolbar, navController, appBarConfiguration);
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
                Toast.makeText(this, "Nothing happened!", Toast.LENGTH_SHORT).show();

                return true;
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
}