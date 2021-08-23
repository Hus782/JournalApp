package com.example.journal22.ui.entries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journal22.R;
import com.example.journal22.UtilsMain;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.ui.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// TODO implement EditEntryViewModel
public class EditEntryFragment extends Fragment {

    //private EditEntryViewModel mViewModel;
    private EditText txtEntry, txtTitle;
    EntryViewModel mEntryViewModel;

    public static EditEntryFragment newInstance() {
        return new EditEntryFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_edit_entry, container, false);

        txtEntry = root.findViewById(R.id.txtEditedEntry);
        txtTitle = root.findViewById(R.id.txtEditedTitle);
        //tv.setText(getArguments().getString("amount"));

        String title = getArguments().getString(Constants.TITLE);
        String content = getArguments().getString(Constants.CONTENT);
        String date = getArguments().getString(Constants.DATE);

        txtEntry.setText(content);
        txtTitle.setText(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(UtilsMain.changeDateFormat(date));

        txtEntry.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // mViewModel = new ViewModelProvider(this).get(EditEntryViewModel.class);


        final FloatingActionButton editBtn = getView().findViewById(R.id.save_edited_btn);

        editBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editEntry();
                        Toast.makeText(getContext(), "Updated (probably)", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.action_editEntry_to_main_fragment);
                    }
                });
    }

    public void editEntry() {
        mEntryViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));

        txtEntry = getView().findViewById(R.id.txtEditedEntry);
        txtTitle = getView().findViewById(R.id.txtEditedTitle);

        int id =Integer.parseInt(getArguments().getString(Constants.ID));
        String title = txtTitle.getText().toString();
        String content = txtEntry.getText().toString();
        String date = getArguments().getString(Constants.DATE);
        String time = getArguments().getString(Constants.TIME);

        long wordsCount = UtilsMain.countWords(content);

        long journalID = mEntryViewModel.getCurrJournal().getValue();// activity.currJournal.getValue();
        if(journalID == -1){
            journalID = 1;
        }

        Entry entry = new Entry(id,title,content,date,time,journalID, wordsCount);

        mEntryViewModel.updateEntry(entry);
    }


    }