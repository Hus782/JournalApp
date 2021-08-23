package com.example.journal22.ui.entries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
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
import com.example.journal22.UtilsMain;
import com.example.journal22.data.entity.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EntriesFragment extends Fragment {
    //MyRecyclerViewAdapter adapter;

    private EntryViewModel mEntryViewModel;

    private RecyclerView recyclerView;
    private EntryListAdapter adapter;
    private FloatingActionButton add_btn;
    private TextView txtUsername, txtTodayDate, txtStreak, txtTotalDays, txtTotalEntries;


    @SuppressLint("DefaultLocale")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // create toolbar options
      //  setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_entries, container, false);

        UtilsMain.hideKeyboard(getActivity());


        //add new entry button
        add_btn = root.findViewById(R.id.add_entry_btn);
        add_btn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        // access parent fragment (try to)
                        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
                        Fragment parent = (Fragment) navHostFragment.getParentFragment();
                        Navigation.findNavController(parent.getView()).navigate(R.id.action_main_fragment_to_new_entry);

                    }
                });

        txtTotalEntries = root.findViewById(R.id.txtTotalEntries);
        txtTotalDays = root.findViewById(R.id.txtTotalDays);

        //set today's date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        txtTodayDate = root.findViewById(R.id.txtTodayDate);
        txtTodayDate.setText(formattedDate);

        mEntryViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));
        //mJournalViewModel= new ViewModelProvider(requireActivity()).get((JournalViewModel.class));

        setUpRecyclerView(root);

        mEntryViewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> {

              adapter.submitList(entries);
            txtTotalEntries.setText(String.format("%d entries", entries.size()));
            mEntryViewModel.getDays().observe(getViewLifecycleOwner(), days -> {
                txtTotalDays.setText(String.format("%d days", days.size()));
            });

          //      Toast.makeText(root.getContext(), "THE SIZE OF WORDS IS :"+ entries.size(),
          //          Toast.LENGTH_SHORT).show();
        });


        return root;
    }

    public void setUpRecyclerView(View root) {

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new EntryListAdapter(new EntryListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //itemDecoration.setDrawable(new ColorDrawable(R.color.black));

        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(root.getContext(),
                recyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                showEntry(view,position);

            }

            @Override
            public void onLongClick(View view, int position) {
                showOptionsDialog(root, view, position);
            }

        }));

    }

    public void showEntry(View view, final int position) {
        //Values are passing to activity & to fragment as well
      /*
        Toast.makeText(view.getContext(), "Single Click on position        :"+position,
                Toast.LENGTH_SHORT).show();


       */
        Entry entry = mEntryViewModel.getEntry(position);
        String id = String.valueOf(entry.getEntry_id());
        String date = entry.getDate();
        String time = entry.getTime();
        TextView txtContent = view.findViewById(R.id.txtContent);
        String content = txtContent.getText().toString();
        TextView txtName = view.findViewById(R.id.txtName);
        String title = txtName.getText().toString();

        Bundle extras = UtilsMain.bundleUp(title, content, date,time, id);//new Bundle();

        // access parent fragment (try to)
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        Fragment parent = (Fragment) navHostFragment.getParentFragment();
        Navigation.findNavController(parent.getView()).navigate(R.id.action_main_fragment_to_display_entry, extras);


    }

    public void showOptionsDialog(View root, View view, final int position) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(root.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    setDialog(root.getContext(),position);
                    return true;

                case R.id.menu_nothing:
                    Toast.makeText(root.getContext(), "Nothing happened!", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.menu_update:
                    Entry entry = mEntryViewModel.getEntry(position);
                    int id = entry.getEntry_id();
                    String title = "Updated title";
                    String content = entry.getContent();
                    String date = entry.getDate();
                    String time = entry.getTime();

                    long journalID = 0;
                    long wordsCount = UtilsMain.countWords(content);

                    Entry updateEntry = new Entry(id,title,content,date,time,journalID,wordsCount);

                    mEntryViewModel.updateEntry(updateEntry);
                    Toast.makeText(root.getContext(), "Updated (probably)", Toast.LENGTH_SHORT).show();



                    return true;
                default:
                    return false;
            }
        });

        popup.show();//showing popup menu
    }


    public void setDialog(Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure mate?");

        // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Entry myEntry = mEntryViewModel.getEntry(position);
                mEntryViewModel.deleteEntry(myEntry);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                Toast.makeText(getContext(), "Nothing happened!", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_template:
                Toast.makeText(getContext(), "Templates showing!", Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    
}

