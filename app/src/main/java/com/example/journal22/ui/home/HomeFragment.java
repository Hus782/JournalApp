package com.example.journal22.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journal22.EntryListAdapter;
import com.example.journal22.MainActivity;
import com.example.journal22.R;
import com.example.journal22.create_entry;
import com.example.journal22.data.Entry;
import com.example.journal22.data.EntryViewModel;
import com.example.journal22.show_entry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

    private MainActivity.ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context, final RecyclerView recycleView, final MainActivity.ClickListener clicklistener){

        this.clicklistener=clicklistener;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && clicklistener!=null){
                    clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
            clicklistener.onClick(child,rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

public class HomeFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    //MyRecyclerViewAdapter adapter;
    private static final String TAG = "MyActivity";

    private EntryViewModel mWordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private EntryListAdapter adapter;
    private HomeViewModel homeViewModel;
    private ImageButton add_btn;
    public void start_entry(View view) {
        Intent intent = new Intent(view.getContext(), create_entry.class);
        startActivityForResult(intent,NEW_WORD_ACTIVITY_REQUEST_CODE);

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // create toolbar options
        setHasOptionsMenu(true);

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);



        //add new entry button
        add_btn = root.findViewById(R.id.add_entry_btn);
        add_btn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(root.getContext(), create_entry.class);
                        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);

                    }
                });



        //entries stuff
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new EntryListAdapter(new EntryListAdapter.WordDiff());

        recyclerView.setAdapter(adapter);

        mWordViewModel = new ViewModelProvider(this).get((EntryViewModel.class));


        mWordViewModel.getAllWords().observe(getViewLifecycleOwner(), words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(root.getContext(),
                recyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(root.getContext(), "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();

                Entry entry = mWordViewModel.getEntry(position);

                TextView editText = view.findViewById(R.id.txtContent);
                String content = editText.getText().toString();
                TextView editText2 = view.findViewById(R.id.txtName);
                String title = editText2.getText().toString();
                Log.v(TAG, content);
                Log.v(TAG, title);
                //Log.v(TAG, "single clicked mateeeeeeeeeeeeeeeeeeeee");

                Intent intent = new Intent(root.getContext(), show_entry.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_TITLE",title);
                extras.putString("EXTRA_CONTENT",content);
                intent.putExtras(extras);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(root.getContext(), view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(item -> {
                    //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    //return true;
                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            setDialog(root.getContext(),position);

                            return true;
                        case R.id.menu_nothing:
                            Toast.makeText(root.getContext(), "Nothing happened!", Toast.LENGTH_SHORT).show();
                            return true;

                        default:
                            return false;
                    }
                });

                popup.show();//showing popup menu
            }




        }));
        return root;
    }
    public void setDialog(Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure mate?");

        // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Entry myWord = mWordViewModel.getEntry(position);
                //Log.v("Got content mate", myWord.getContent());
                mWordViewModel.deleteWord(myWord);
                Toast.makeText(context, "Item deleted!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "we in main activity");

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String title = extras.getString("EXTRA_TITLE");
            String content = extras.getString("EXTRA_CONTENT");
            String date = extras.getString("EXTRA_DATE");

            Log.v(TAG, "we in main activity again");

            Log.v(TAG, title);

            Log.v(TAG, content);

            Entry word = new Entry(title,content,date);
            mWordViewModel.insert(word);
        } /*else {
            Toast.makeText(
                    getApplicationContext(),
                    "Oops",
                    Toast.LENGTH_LONG).show();
        }*/
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_menu, menu) ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getContext(), "Nothing happened!", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.action_template:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getContext(), "Templates showing!", Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}

