package com.example.journal22.ui.entries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journal22.MainActivity;
import com.example.journal22.R;
import com.example.journal22.data.JournalViewModel;
import com.example.journal22.old.create_entry;
import com.example.journal22.data.entity.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EntriesFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //MyRecyclerViewAdapter adapter;
    private static final String TAG = "MyActivity";

    private EntryViewModel mEntryViewModel;
    private JournalViewModel mJournalViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private EntryListAdapter adapter;
    private EntriesViewModel homeViewModel;
    private FloatingActionButton add_btn;
    private TextView txtUsername, txtTodayDate, txtStreak, txtTotalDays, txtTotalEntries;
    public void start_entry(View view) {
        Intent intent = new Intent(view.getContext(), create_entry.class);
        startActivityForResult(intent,NEW_WORD_ACTIVITY_REQUEST_CODE);

    }


    @SuppressLint("DefaultLocale")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // create toolbar options
        setHasOptionsMenu(true);

        homeViewModel =
                new ViewModelProvider(this).get(EntriesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_entries, container, false);



        //add new entry button
        add_btn = root.findViewById(R.id.add_entry_btn);
        add_btn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                       // Intent intent = new Intent(root.getContext(), create_entry.class);
                       // startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                        // access parent fragment (try to)
                        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
                        Fragment parent = (Fragment) navHostFragment.getParentFragment();

                        Navigation.findNavController(parent.getView()).navigate(R.id.action_main_fragment_to_new_entry);

                    }
                });

        txtTotalEntries = root.findViewById(R.id.txtTotalEntries);


        //set today's date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        txtTodayDate = root.findViewById(R.id.txtTodayDate);
        txtTodayDate.setText(formattedDate);


        //entries stuff
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new EntryListAdapter(new EntryListAdapter.WordDiff());

        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //itemDecoration.setDrawable(new ColorDrawable(R.color.black));

        recyclerView.addItemDecoration(itemDecoration);


        mEntryViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));
        mJournalViewModel= new ViewModelProvider(requireActivity()).get((JournalViewModel.class));
        MainActivity activity = ((MainActivity)getActivity());
       // activity.setChecked();
/*
        MainActivity activity = ((MainActivity)getActivity());
        activity.currJournal.observe(getViewLifecycleOwner(), id -> {
            mEntryViewModel.updateJournalID(id);
            Log.v("AAAAAAAAAAA", "Updated journalID!\n" );

        });
*/

   //       Journal journal = new Journal("Journal3");
 //       mJournalViewModel.insert(journal);
//
//        mJournalViewModel.getJournalsAndEntries();
//        Log.v(TAG, "Testing that shiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiit");
//        Log.v(TAG, mJournalViewModel.getJournalsAndEntries().getValue().get(0).Journal.getTitle());
       // mEntryViewModel.getAllWords().observe(getViewLifecycleOwner(), entries -> {
     //       mJournalViewModel.getJournalsAndEntries().observe(getViewLifecycleOwner(), entries -> {
        mEntryViewModel.currJournal.observe(getViewLifecycleOwner(), id -> {
       //     mEntryViewModel.updateJournalID(id);

        });
       // MediatorLiveData liveDataMerger = new MediatorLiveData<>();
       // liveDataMerger.addSource(mEntryViewModel.getAllWords(), entries -> adapter.submitList(entries););



        mEntryViewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> {

            // Update the cached copy of the words in the adapter.
           // adapter.submitList(entries.get(1).entries);
              adapter.submitList(entries);

            txtTotalEntries.setText(String.format("%d entries", entries.size()));
               // txtTotalEntries.setText(String.format("%s journals", entries.get(0).Journal.getJournal_id()));
            Log.v("INSIDE OBSERVER", "THE SIZE OF WORDS IS :"+ entries.size() );

                Toast.makeText(root.getContext(), "THE SIZE OF WORDS IS :"+ entries.size(),
                    Toast.LENGTH_SHORT).show();
        });


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(root.getContext(),
                recyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(root.getContext(), "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();
                //Log.v("AAAAAAAAAAA", String.valueOf(position) );
               // Log.v("all  entries are  \n\n\n", String.valueOf(mEntryViewModel.getSize()) );
                ;;
                Entry entry = mEntryViewModel.getEntry(position);
                int id = entry.getEntry_id();
                String date = entry.getDate();
                TextView editText = view.findViewById(R.id.txtContent);
                String content = editText.getText().toString();
                TextView editText2 = view.findViewById(R.id.txtName);
                String title = editText2.getText().toString();

                //Log.v(TAG, String.valueOf(id) );

                //Log.v(TAG, content);
                //Log.v(TAG, title);

                //Intent intent = new Intent(root.getContext(), show_entry.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_ID",String.valueOf(id));
                extras.putString("EXTRA_TITLE",title);
                extras.putString("EXTRA_CONTENT",content);
                extras.putString("EXTRA_DATE",date);

                //intent.putExtras(extras);

                // access parent fragment (try to)
                NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
                Fragment parent = (Fragment) navHostFragment.getParentFragment();
                //parent.getView().findViewById(R.id.element_id);

/*
                FragmentNavigator.Extras extras2 = new FragmentNavigator.Extras.Builder()
                        .addSharedElement(view, "hero_image")
                        .build();
                Navigation.findNavController(parent.getView()).navigate(
                        R.id.action_main_fragment_to_display_entry,
                        extras, // Bundle of args
                        null, // NavOptions
                        extras2);
*/



                Navigation.findNavController(parent.getView()).navigate(R.id.action_main_fragment_to_display_entry, extras);



                //Entry newEntry = new Entry("test","testContent",date);
                //mWordViewModel.insert(newEntry);
                //startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                //mEntryViewModel.updateJournalID(2);
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(root.getContext(), view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(item -> {
                    //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    //return true;
                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            setDialog(root.getContext(),position);

                            return true;
                        case R.id.menu_nothing:
                            Toast.makeText(root.getContext(), "Nothing happened!", Toast.LENGTH_SHORT).show();
                            return true;

                        case R.id.menu_update:

                            Entry myWord = mEntryViewModel.getEntry(position);
                            int id = myWord.getEntry_id();
                            String title = "Updated title";
                            String content = myWord.getContent();
                            String date = myWord.getDate();
                            long journalID = 0;
                            Entry entry = new Entry(id,title,content,date,journalID);

                            //Log.v("Got content mate", myWord.getContent());
                            mEntryViewModel.updateEntry(entry);
                            Toast.makeText(root.getContext(), "Updated (probably)", Toast.LENGTH_SHORT).show();



                            return true;
                        default:
                            return false;
                    }
                });

                popup.show();//showing popup menu
            }




        }));
        return root;
    }
    public void setDialog(Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure mate?");

        // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Entry myWord = mEntryViewModel.getEntry(position);
                //Log.v("Got content mate", myWord.getContent());
                mEntryViewModel.deleteEntry(myWord);
                Toast.makeText(context, "Item deleted!", Toast.LENGTH_SHORT).show();
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

/*
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
        } /*else {
            Toast.makeText(
                    getApplicationContext(),
                    "Oops",
                    Toast.LENGTH_LONG).show();
        }*/



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        inflater.inflate(R.menu.my_menu, menu) ;

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s != null) {

                    //Log.v(TAG, mWordViewModel.getFilteredEntries(s).toString());

                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //searchReceived(s);
                //Log.v(TAG, mWordViewModel.getFilteredEntries(s).toString());

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getContext(), "Nothing happened!", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_template:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getContext(), "Templates showing!", Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



}

