package com.example.journal22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.journal22.data.Entry;
import com.example.journal22.data.EntryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import androidx.appcompat.widget.Toolbar;

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
        String date = getArguments().getString("EXTRA_DATE");

        txtEntry.setText(content);
        txtTitle.setText(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(changeDateFormat(date));



        return root;
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DisplayEntryViewModel.class);

        //save entry FAB
        final FloatingActionButton editBtn = getView().findViewById(R.id.edit_btn);
        //final FloatingActionButton saveEditedFab = getView().findViewById(R.id.save_edited_btn);

        editBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String id = getArguments().getString("EXTRA_ID");
                        String title = getArguments().getString("EXTRA_TITLE");
                        String content = getArguments().getString("EXTRA_CONTENT");
                        String date = getArguments().getString("EXTRA_DATE");

                        Bundle extras = new Bundle();
                        extras.putString("EXTRA_TITLE",title);
                        extras.putString("EXTRA_CONTENT",content);
                        extras.putString("EXTRA_DATE",date);

                        extras.putString("EXTRA_ID",id);

                        Navigation.findNavController(view).navigate(R.id.action_display_entry_to_editEntry,extras);

                        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
                }
                });


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
}

