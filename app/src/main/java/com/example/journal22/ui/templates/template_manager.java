package com.example.journal22.ui.templates;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journal22.EntryListAdapter;
import com.example.journal22.MainActivity;
import com.example.journal22.R;
import com.example.journal22.data.Entry;
import com.example.journal22.data.EntryViewModel;
import com.example.journal22.data.Template;
import com.example.journal22.data.TemplateViewModel;
import com.example.journal22.old.show_entry;
import com.example.journal22.ui.entries.EntriesViewModel;
import com.example.journal22.ui.entries.RecyclerTouchListener;

public class template_manager extends Fragment {

    private TemplateManagerViewModel mViewModel;
    private RecyclerView recyclerView;
    private TemplateListAdapter adapter;
    private EntriesViewModel homeViewModel;
    private TemplateViewModel mTemplateViewModel;


    public static template_manager newInstance() {
        return new template_manager();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.template_manager_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TemplateManagerViewModel.class);
        // TODO: Use the ViewModel

        //entries stuff
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false);
        //recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new TemplateListAdapter(new TemplateListAdapter.TempDiff());

        recyclerView.setAdapter(adapter);

        mTemplateViewModel = new ViewModelProvider(requireActivity()).get((TemplateViewModel.class));

        mTemplateViewModel.getAllTemps().observe(getViewLifecycleOwner(), temps -> {
            // Update the cached copy of the temps in the adapter.
            adapter.submitList(temps);
        });


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                recyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(getContext(), "Single Click on position        :" + position,
                        Toast.LENGTH_SHORT).show();


               // Navigation.findNavController(parent.getView()).navigate(R.id.action_main_fragment_to_display_entry, extras);
                //startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getContext(), view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(item -> {
                    //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    //return true;
                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                           // setDialog(getContext(),position);

                            return true;
                        case R.id.menu_nothing:
                            Toast.makeText(getContext(), "Nothing happened!", Toast.LENGTH_SHORT).show();
                            return true;

                        default:
                            return false;
                    }
                });

                popup.show();//showing popup menu
            }

         }));



    }

}