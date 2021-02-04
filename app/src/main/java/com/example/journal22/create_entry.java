package com.example.journal22;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class create_entry extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final String TAG = "MyActivity";
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private static final String NumberedTemplate = "1)\n" +
            "2) I\n" +
            "3)";

    FloatingActionButton mAddFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
/*
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Log.v(TAG, mydate);
       // String newstring = new SimpleDateFormat("yyyy-MM-dd").format(mydate);
       // Log.v(TAG, newstring);

        Date c = Calendar.getInstance().getTime();
        Log.v(TAG,"Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        String formattedDate = df.format(c);
        String day = new SimpleDateFormat("dd",   Locale.getDefault()).format(c);
        String month = new SimpleDateFormat("MMM",   Locale.getDefault()).format(c);
        String year = new SimpleDateFormat("yyyy",   Locale.getDefault()).format(c);



        Log.v(TAG,"" + formattedDate);
        Log.v(TAG,"" + day);
*/
        // my_child_toolbar is defined in the layout file


        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myChildToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

         mAddFab = findViewById(R.id.save_entry_btn);

        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText editText = (EditText) findViewById(R.id.txtEntry);
                        String content = editText.getText().toString();
                        EditText editText2 = (EditText) findViewById(R.id.txtTitle);
                        String title = editText2.getText().toString();
                        Log.v(TAG, content);
                        Toast.makeText(create_entry.this, "Entry Added", Toast.LENGTH_SHORT).show();


                        Date c = Calendar.getInstance().getTime();
                        Log.v(TAG,"Current time => " + c);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy-EEEE-HH:mm", Locale.getDefault());

                        String formattedDate = df.format(c);
                        Log.v(TAG,formattedDate);

                        Intent intent = new Intent(create_entry.this, MainActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("EXTRA_TITLE",title);
                        extras.putString("EXTRA_CONTENT",content);
                        extras.putString("EXTRA_DATE",formattedDate);

                        intent.putExtras(extras);
                        setResult(RESULT_OK, intent);
                        Log.v(TAG, "send ok");

                        finish();

                    }
                });
    }


    /** Called when the user taps the Send button */
    public void save_entry(View view) {
        EditText editText = (EditText) findViewById(R.id.txtEntry);
        String message = editText.getText().toString();
        Log.v(TAG, message);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "Nothing happened!", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.action_template:
                // User chose the "Settings" item, show the app settings UI...
                //Toast.makeText(this, "Templates showing!", Toast.LENGTH_SHORT).show();

                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose a template");
                // add a list
                String[] list = {"Normal", "Numbered list"};
                builder.setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                            case 1:
                                EditText editText = (EditText) findViewById(R.id.txtEntry);
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