package com.example.journal22;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class display_entry extends Fragment {

    private DisplayEntryViewModel mViewModel;
    private TextView txtEntry, txtTitle;

    public static display_entry newInstance() {
        return new display_entry();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.display_entry_fragment, container, false);



        txtEntry = root.findViewById(R.id.txtEntry);
        txtTitle = root.findViewById(R.id.txtTitle);
        //tv.setText(getArguments().getString("amount"));

        String title = getArguments().getString("EXTRA_TITLE");
        String content = getArguments().getString("EXTRA_CONTENT");
        txtEntry.setText(content);
        txtTitle.setText(title);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DisplayEntryViewModel.class);
        // TODO: Use the ViewModel


    }

}