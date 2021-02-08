package com.example.journal22.ui.templates;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.journal22.EntryListAdapter;
import com.example.journal22.R;
import com.example.journal22.data.EntryViewModel;
import com.example.journal22.data.Template;
import com.example.journal22.data.TemplateViewModel;
import com.example.journal22.ui.entries.EntriesViewModel;

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

        mTemplateViewModel.getAllTemps().observe(getViewLifecycleOwner(), words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });
    }

}