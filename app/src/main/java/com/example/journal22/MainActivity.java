package com.example.journal22;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
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
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private NavController navController;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        // Show and Manage the Drawer and Back Icon
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        navView = findViewById(R.id.nav_view);
        setMenu();
        NavigationUI.setupWithNavController(navView, navController);



    }
    public Menu getMenu() {
        return menu;
    }
    public void setMenu() {
        JournalViewModel viewModel = new ViewModelProvider(this).get(JournalViewModel.class);
        EntryViewModel mEntryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(EntryViewModel.class);
        menu = navView.getMenu();
        viewModel.getJournalsAndCount().observe(this, item -> {

            int journSize = item.size();
          //  Log.v(TAG, String.valueOf(journSize));
         //   Log.v(TAG, "Updating Journals and items and stuff");

            //Toast.makeText(this, journSize, Toast.LENGTH_SHORT).show();

            menu.removeGroup(3);
            menu.add(3, -1,Menu.NONE,"All ").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mEntryViewModel.setCurrJournal(-1);
                    drawerLayout.close();
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
                int id = item.get(i).journal.getJournal_id();
                String title = item.get(i).journal.getTitle() + " (" + item.get(i).entries_count + ")";
                if(menu.findItem(id)==null){
                    menu.add(3, id,Menu.NONE,title).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        //menu.add(3, id,Menu.NONE,title).setCheckable(true).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
   //                         Log.v(TAG, String.valueOf("Clicked "  + id));
/*
                            for (int i = 0; i < menu.size(); i++) {
                                Log.v("onMenuItemClick", menu.getItem(i).getTitle().toString());

                                SpannableString s = new SpannableString(menu.getItem(i).getTitle().toString());
                                s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length(), 0);
                                menu.findItem(i).setTitle(s);



                            }
*/
                        //    SpannableString s = new SpannableString(title);
                        //    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                            //currJournal.setValue(id);
                         //    menu.findItem(id).setTitle(s);
                            //menu.findItem(id).setChecked(true);
                            //navView.setCheckedItem(id);
                            //      navController.navigate(R.id.action_main_fragment_self);
                            //mEntryViewModel.currJournal.setValue(id);
                            mEntryViewModel.setCurrJournal(id);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "Nothing happened!", Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }
    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}