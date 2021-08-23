package com.example.journal22;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.journal22.data.JournalViewModel;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.data.entity.Journal;
import com.example.journal22.ui.entries.EntryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.spec.ECField;
import java.util.List;

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }
    private EntryViewModel mEntryViewModel;
    private JournalViewModel mJournalViewModel ;
    Menu menu;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);

        mEntryViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));
        mJournalViewModel = new ViewModelProvider(requireActivity()).get((JournalViewModel.class));
/*
        LiveData<List<Journal>> allJournals = Transformations.switchMap(mEntryViewModel.getCurrJournal(), query ->
                mJournalViewModel.getAllJournals()
        );
        Log.v("TAG", "Got called here!\n\n\n\n\n\n");


 */
        mEntryViewModel.getCurrJournal().observe(getViewLifecycleOwner(), id -> {
            Log.v("TAG", "Got called here!\n\n\n\n\n\n");

            MainActivity activity = (MainActivity)getActivity();
            menu = activity.getMenu();
            if(menu == null){
                Toast.makeText(getContext(), "IT IS NULL MATE", Toast.LENGTH_SHORT).show();

            }

            mJournalViewModel.getAllJournals().observe(getViewLifecycleOwner(), journs -> {

            for (int i = -1; i <= journs.size(); i++) {

                    onSetColorItem(i, Color.BLACK);

            }
            try {
                MenuItem item = menu.findItem(id);
                SpannableString s = new SpannableString(item.getTitle());
                s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
                menu.findItem(id).setTitle(s);
            }
            catch (Exception ignored){
            }

            });
        });

        return root;
    }

    public void onSetColorItem(int id, int color) {
        try {
        SpannableString s = new SpannableString(menu.findItem(id).getTitle().toString());
        s.setSpan(new ForegroundColorSpan(color), 0, s.length(), 0);
        menu.findItem(id).setTitle(s);
        }
                catch (Exception ignored){

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navView = view.findViewById(R.id.bottom_nav_view);
/*
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

 */
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);




    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


       // mViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        // TODO: Implement the ViewModel



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.my_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int item_id = item.getItemId();

        switch (item_id) {


            case R.id.action_template_manager:
                Toast.makeText(getContext(), "Templates showing!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(R.id.action_main_fragment_to_template_manager);

                return true;

            case R.id.action_search:
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