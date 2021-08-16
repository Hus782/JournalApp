package com.example.journal22.ui.entries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journal22.R;
import com.example.journal22.UtilsMain;
import com.example.journal22.ui.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// TODO implement DisplayEntryViewModel
public class ShowEntryFragment extends Fragment {

    //private DisplayEntryViewModel mViewModel;
    private TextView txtEntry, txtTitle;
    private String title, content, date, id;
    public static ShowEntryFragment newInstance() {
        return new ShowEntryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_show_entry, container, false);

        UtilsMain.hideKeyboard(getActivity());



        txtEntry = root.findViewById(R.id.txtEntry);
        txtTitle = root.findViewById(R.id.txtTitle);
        //tv.setText(getArguments().getString("amount"));

        title = getArguments().getString(Constants.TITLE);
        content = getArguments().getString(Constants.CONTENT);
        date = getArguments().getString(Constants.DATE);
        id = getArguments().getString(Constants.ID);

        txtEntry.setMovementMethod(new ScrollingMovementMethod());

        txtEntry.setText(content);
        txtTitle.setText(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(UtilsMain.changeDateFormat(date));



        //edit entry FAB
        final FloatingActionButton editBtn = root.findViewById(R.id.edit_btn);

        editBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle extras = UtilsMain.bundleUp(title, content, date, id);//new Bundle();
                        Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.action_display_entry_to_editEntry,extras);

                    }
                });

        return root;
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     //   mViewModel = new ViewModelProvider(this).get(DisplayEntryViewModel.class);



    }

}

