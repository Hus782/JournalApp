package com.example.journal22;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journal22.data.Template;
import com.example.journal22.data.TemplateViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class new_entry extends Fragment {

    private NewEntryViewModel mViewModel;
    List<Template> temps;
    private static final String NumberedTemplate = "1)\n" +
            "2) I\n" +
            "3)";

    FloatingActionButton mAddFab;
    private TemplateViewModel mTemplateViewModel;

    public static new_entry newInstance() {
        return new new_entry();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.new_entry_fragment, container, false);


        mTemplateViewModel = new ViewModelProvider(this).get((TemplateViewModel.class));


        mTemplateViewModel.getAllTemps().observe(getViewLifecycleOwner(), words -> {

            temps=words;

        });

        //save entry FAB
        mAddFab = root.findViewById(R.id.save_entry_btn);

        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText editText = (EditText) root.findViewById(R.id.txtEntry);
                        String content = editText.getText().toString();
                        EditText editText2 = (EditText) root.findViewById(R.id.txtTitle);
                        String title = editText2.getText().toString();
                        Log.v("TAG", content);
                        Toast.makeText(getContext(), "Entry Added", Toast.LENGTH_SHORT).show();


                        Date c = Calendar.getInstance().getTime();
                        Log.v("TAG","Current time => " + c);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());

                        String formattedDate = df.format(c);
                        Log.v("TAG",formattedDate);

                        //Intent intent = new Intent(create_entry.this, MainActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("EXTRA_TITLE",title);
                        extras.putString("EXTRA_CONTENT",content);
                        extras.putString("EXTRA_DATE",formattedDate);

                    //    intent.putExtras(extras);
                     //   setResult(RESULT_OK, intent);
                        Log.v("TAG", "send ok");

                      //  finish();

                    }
                });
        return root;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewEntryViewModel.class);
        // TODO: Use the ViewModel
    }

}