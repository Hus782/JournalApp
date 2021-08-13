package com.example.journal22.ui.entries;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journal22.MainActivity;
import com.example.journal22.R;
import com.example.journal22.Utils;
import com.example.journal22.data.entity.Entry;
import com.example.journal22.old.NewEntryViewModel;
import com.example.journal22.data.entity.Template;
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

        setHasOptionsMenu(true);

        EditText entry = root.findViewById(R.id.txtEntry);
        try{
            String body = getArguments().getString("EXTRA_CONTENT");
            if(!body.isEmpty()){
                entry.setText(body);
            }
        }
        catch (Exception ignored){

        }
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

                        createEntry(content, title);
                        // navigate back to main fragment
                        Navigation.findNavController(view).navigate(R.id.action_new_entry_to_main_fragment);

                    }
                });
        return root;

    }

    public String getFormattedDate() {
        Date c = Calendar.getInstance().getTime();
        Log.v("TAG","Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());

        String formattedDate = df.format(c);
        Log.v("TAG",formattedDate);
        return formattedDate;
    }

    public void createEntry(String content, String title) {
        String formattedDate = getFormattedDate();
        // create shared ViewModel to insert safely
        EntryViewModel mWordViewModel = new ViewModelProvider(requireActivity()).get((EntryViewModel.class));
        long journalID = mWordViewModel.currJournal.getValue();// activity.currJournal.getValue();
        if(journalID == -1){
            journalID = 1;
        }
        long wordsCount = Utils.countWords(content);

        Entry word = new Entry(title,content,formattedDate,journalID, wordsCount);
        mWordViewModel.insertEntry(word);

        Toast.makeText(getContext(), "Entry Added", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(NewEntryViewModel.class);
        mTemplateViewModel = new ViewModelProvider(this).get((TemplateViewModel.class));

        mTemplateViewModel.getAllTemps().observe(getViewLifecycleOwner(), words -> {

            temps=words;

        });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.create_entry_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getContext(), "Nothing happened!", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.action_create_template:
                // User chose the "Settings" item, show the app settings UI...
                EditText editText = (EditText) getView().findViewById(R.id.txtEntry);
                String content = editText.getText().toString();


                //ask for template title
                AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                final EditText edittext = new EditText(requireContext());
                alert.setMessage("Enter template title");
                alert.setTitle("Create template");
                alert.setView(edittext);
                alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String title = edittext.getText().toString();
                        if(title.isEmpty()){
                            title = "Template " + temps.size();
                        }

                        //Toast.makeText(create_entry.this, YouEditTextValue, Toast.LENGTH_SHORT).show();
                        Template entry = new Template(title,content);
                        mTemplateViewModel.insert(entry);
                        Toast.makeText(getContext(), "Saved as template!", Toast.LENGTH_SHORT).show();

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();

                return true;

            case R.id.action_template:
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose a template");
                // add a list
                //String[] list = {"Normal", "Numbered list"};
                String[] list = new String[temps.size()];

                for (int i=0; i<temps.size(); i++){
                    list[i] = temps.get(i).getTitle();
                }

                builder.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                            case 1:
                                EditText editText = (EditText) getView().findViewById(R.id.txtEntry);
                                editText.setText(NumberedTemplate);

                        }
                    }
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}