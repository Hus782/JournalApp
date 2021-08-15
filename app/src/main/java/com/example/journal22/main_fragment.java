package com.example.journal22;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journal22.data.entity.Entry;
import com.example.journal22.old.MainFragmentViewModel;
import com.example.journal22.ui.entries.EntryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class main_fragment extends Fragment {

    private MainFragmentViewModel mViewModel;
    DrawerLayout drawer;
    ActionBarDrawerToggle toogle;
    NavigationView nv;


    public static main_fragment newInstance() {
        return new main_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.main_fragment_fragment, container, false);
        setHasOptionsMenu(true);



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navView = view.findViewById(R.id.bottom_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);



/*
        EntryViewModel mEntryViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));

        MainActivity activity = ((MainActivity)getActivity());
        activity.currJournal.observe(getViewLifecycleOwner(), id -> {
            mEntryViewModel.updateJournalID(id);
            Log.v("AAAAAAAAAAA", "Updated journalID!\n" );

        });
*/
        //drawer = getActivity().findViewById(R.id.drawer_layout);


        //View view = findViewById(R.id.drawer_layout);
        // drawer stuff
        //Toolbar myToolbar = getActivity().findViewById(R.id.my_toolbar);

       // drawer = getActivity().findViewById(R.id.drawer_layout);



        //toogle = new ActionBarDrawerToggle(getActivity(), drawer,R.string.open, R.string.closed);
       // drawer.addDrawerListener(toogle);
        //toogle.syncState();


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        // TODO: Use the ViewModel



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.my_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


/*

        MenuItem searchViewItem
                = menu.findItem(R.id.action_search);
        SearchView searchView
                = (SearchView) MenuItemCompat
                .getActionView(searchViewItem);

 */
        /*
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(searchViewItem, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(searchViewItem, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });
         */

       // MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) searchItem.getActionView();

    /*    searchView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Navigation.findNavController(view).navigate(R.id.action_main_fragment_to_searchFragment);

                    }
                });

     */
        /*
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        EntryViewModel mEntryViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));

                        Log.v("onQueryTextSubmit", query);
                    //    LiveData<List<Entry>> entries = mEntryViewModel.getFilteredEntries(query);
                       // Log.v("onQueryTextSubmit size", String.valueOf(entries.getValue()==null));
                        mEntryViewModel.getFilteredEntries(query).observe(getViewLifecycleOwner(), entries -> {

                            Log.v("INSIDE OBSERVER", "THE SIZE OF WORDS IS :"+ entries.size() );
                            Log.v("onQueryTextSubmit size", String.valueOf(entries.size()));

                        });


                        Toast.makeText(getView().getContext(), query,
                                Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    // This method is overridden to filter
                    // the adapter according to a search query
                    // when the user is typing search
                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        // Search query not found in List View
                        Log.v("onQueryTextChange", newText);

                        Toast.makeText(getView().getContext(), "onQueryTextChange",
                                Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });



         */




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int item_id = item.getItemId();

        switch (item_id) {


            case R.id.action_template_manager:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getContext(), "Templates showing!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(R.id.action_main_fragment_to_template_manager);

                return true;

            case R.id.action_search:
                // User chose the "Settings" item, show the app settings UI...
              //  Toast.makeText(getContext(), "Templates showing!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(R.id.action_main_fragment_to_searchFragment);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
              //  Toast.makeText(getContext(), "Journal with id=" + String.valueOf(item_id),Toast.LENGTH_SHORT).show();

                return super.onOptionsItemSelected(item);

        }
    }

}