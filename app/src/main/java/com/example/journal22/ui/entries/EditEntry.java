package com.example.journal22.ui.entries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journal22.R;
import com.example.journal22.UtilsMain;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.ui.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditEntry extends Fragment {

    private EditEntryViewModel mViewModel;
    private EditText txtEntry, txtTitle;
    EntryViewModel mWordViewModel;

    public static EditEntry newInstance() {
        return new EditEntry();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.edit_entry_fragment, container, false);

        txtEntry = root.findViewById(R.id.txtEditedEntry);
        txtTitle = root.findViewById(R.id.txtEditedTitle);
        //tv.setText(getArguments().getString("amount"));

        String title = getArguments().getString(Constants.TITLE);
        String content = getArguments().getString(Constants.CONTENT);
        String date = getArguments().getString(Constants.DATE);

        txtEntry.setText(content);
        txtTitle.setText(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(changeDateFormat(date));

        return root;
    }

    public String changeDateFormat(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());
        Date mydate;
        try {
            mydate = format.parse(date);


        } catch (ParseException e) {
            e.printStackTrace();
            return "day";

        }
        String WeekDay = new SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.getDefault()).format(mydate);
        return WeekDay;


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditEntryViewModel.class);
        // TODO: Use the ViewModel


        final FloatingActionButton editBtn = getView().findViewById(R.id.save_edited_btn);

        editBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mWordViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));

                        txtEntry = getView().findViewById(R.id.txtEditedEntry);
                        txtTitle = getView().findViewById(R.id.txtEditedTitle);

                        int id =Integer.parseInt(getArguments().getString(Constants.ID));
                        String title = txtTitle.getText().toString();
                        String content = txtEntry.getText().toString();
                        String date = getArguments().getString(Constants.DATE);
                        long wordsCount = UtilsMain.countWords(content);

                        long journalID = mWordViewModel.currJournal.getValue();// activity.currJournal.getValue();
                        if(journalID == -1){
                            journalID = 1;
                        }

                        Entry entry = new Entry(id,title,content,date,journalID, wordsCount);
                        //Log.v("TAG", String.valueOf(id) );

                        //Log.v("TAG", title );
                        //Log.v("TAG", content );
                        //Log.v("Got content mate", myWord.getContent());
                        mWordViewModel.updateEntry(entry);
                        Toast.makeText(getContext(), "Updated (probably)", Toast.LENGTH_SHORT).show();




                        Navigation.findNavController(view).navigate(R.id.action_editEntry_to_main_fragment);

                        //Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    }