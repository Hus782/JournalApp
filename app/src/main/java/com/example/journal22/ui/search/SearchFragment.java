package com.example.journal22.ui.search;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journal22.MainActivity;
import com.example.journal22.R;
import com.example.journal22.UtilsMain;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.ui.entries.EntryListAdapter;
import com.example.journal22.ui.entries.EntryViewModel;
import com.example.journal22.ui.entries.RecyclerTouchListener;

import java.util.List;

public class SearchFragment extends Fragment {

    private EntryViewModel mEntryViewModel;
    private SearchViewModel mViewModel;
    private RecyclerView recyclerView;
    private EntryListAdapter adapter;
    private MutableLiveData<String> mFilteredEntries = new MutableLiveData<String>();

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View root =  inflater.inflate(R.layout.fragment_search, container, false);


        setUpRecyclerView(root);

        mEntryViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));

        LiveData<List<Entry>> allEntries = Transformations.switchMap(mFilteredEntries, query ->
                        mEntryViewModel.getFilteredEntries(query)
                );

        allEntries.observe(getViewLifecycleOwner(), entries -> {
            adapter.submitList(entries);
        });

        return root;
    }

    public void setUpRecyclerView(View root) {


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
        Toast.makeText(view.getContext(), "Single Click on position        :"+position,
                Toast.LENGTH_SHORT).show();

        Entry entry = mEntryViewModel.getEntry(position);
        String id = String.valueOf(entry.getEntry_id());
        String date = entry.getDate();
        String time = entry.getTime();

        TextView txtContent = view.findViewById(R.id.txtContent);
        String content = txtContent.getText().toString();
        TextView txtName = view.findViewById(R.id.txtName);
        String title = txtName.getText().toString();

        Bundle extras = UtilsMain.bundleUp(title, content, date, time,id);//new Bundle();



        // access parent fragment (try to)
        Navigation.findNavController(getView()).navigate(R.id.action_searchFragment_to_display_entry, extras);


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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

       // onOptionsItemSelected(menu.findItem(R.id.action_search));

        // TODO: Use the ViewModel
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


        MenuItem searchViewItem
                = menu.findItem(R.id.action_search);
        SearchView searchView
                = (SearchView) MenuItemCompat
                .getActionView(searchViewItem);

        searchView.performClick();
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        EntryViewModel mEntryViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));
                        Log.v("onQueryTextSubmit", query);
                        mFilteredEntries.setValue(query);
                      /*
                        mEntryViewModel.getFilteredEntries(query).observe(getViewLifecycleOwner(), entries -> {

                            Log.v("INSIDE OBSERVER", "THE SIZE OF WORDS IS :"+ entries.size() );
                            Log.v("onQueryTextSubmit size", String.valueOf(entries.size()));

                        });

                       */


                      //  Toast.makeText(getView().getContext(), query,
                     //           Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    // This method is overridden to filter
                    // the adapter according to a search query
                    // when the user is typing search
                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        Log.v("onQueryTextChange", newText);
                    //    Toast.makeText(getView().getContext(), "onQueryTextChange",
                    //            Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
        searchView.setIconified(false);

    }
}