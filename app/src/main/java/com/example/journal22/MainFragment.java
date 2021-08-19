package com.example.journal22;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);


        return root;
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