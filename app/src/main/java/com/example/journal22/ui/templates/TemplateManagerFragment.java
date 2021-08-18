package com.example.journal22.ui.templates;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.journal22.MainActivity;
import com.example.journal22.R;
import com.example.journal22.UtilsMain;
import com.example.journal22.data.entity.Template;
import com.example.journal22.ui.entries.RecyclerTouchListener;

public class TemplateManagerFragment extends Fragment {

    private TemplateManagerViewModel mViewModel;
    private RecyclerView recyclerView;
    private TemplateListAdapter adapter;
   // private EntriesViewModel homeViewModel;
    private TemplateViewModel mTemplateViewModel;


    public static TemplateManagerFragment newInstance() {
        return new TemplateManagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.shared_element_transition));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_manager, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TemplateManagerViewModel.class);
        // TODO: Use the ViewModel
        setUpRecyclerView(getView());

    }

    public void setUpRecyclerView(View root) {

        //templates stuff
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new TemplateListAdapter(new TemplateListAdapter.TempDiff());
        recyclerView.setAdapter(adapter);
        mTemplateViewModel = new ViewModelProvider(requireActivity()).get((TemplateViewModel.class));
        mTemplateViewModel.getAllTemps().observe(getViewLifecycleOwner(), temps -> {
            // Update the cached copy of the temps in the adapter.
            adapter.submitList(temps);
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                recyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                showTemplate(position);
            }
            @Override
            public void onLongClick(View view, int position) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getContext(), view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(item -> {
                    //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    //return true;
                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            setDialog(getContext(),position);

                            return true;
                        case R.id.menu_nothing:
                            Toast.makeText(getContext(), "Nothing happened!", Toast.LENGTH_SHORT).show();
                            return true;

                        default:
                            return false;
                    }
                });

                popup.show();//showing popup menu
            }

        }));

    }

    public void showTemplate(final int position) {
        //Values are passing to activity & to fragment as well
       /*
        Toast.makeText(getContext(), "Single Click on position:  " + position,
                Toast.LENGTH_SHORT).show();


        */
        Template temp = mTemplateViewModel.getTemplate(position);
        String id = String.valueOf(temp.getTemplate_id());
        String title = temp.getTitle();
        String content = temp.getContent();

        Bundle extras = UtilsMain.bundleUp(title, content, null, id);//new Bundle();

        // access parent fragment (try to)
        Navigation.findNavController(getView()).navigate(R.id.action_template_manager_to_display_template, extras);



    }


    public void setDialog(Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure mate?");

        // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Template myWord = mTemplateViewModel.getTemplate(position);
                //Log.v("Got content mate", myWord.getContent());
                mTemplateViewModel.deleteTemplate(myWord);
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

}