package com.example.journal22.ui.templates;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journal22.R;
import com.example.journal22.data.Template;

public class display_template extends Fragment {

    private DisplayTemplateViewModel mViewModel;
    private TextView txtContent, txtTitle;

    public static display_template newInstance() {
        return new display_template();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_exit));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.display_template_fragment, container, false);

        setHasOptionsMenu(true);

        txtContent = root.findViewById(R.id.txtBody);
        txtTitle = root.findViewById(R.id.txtTitle);

        String title = getArguments().getString("EXTRA_TITLE");
        String content = getArguments().getString("EXTRA_CONTENT");

        txtContent.setText(content);
        txtTitle.setText(title);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DisplayTemplateViewModel.class);
        // TODO: Use the ViewModel


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.template_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_use:


                TextView editText = getView().findViewById(R.id.txtBody);
                String content = editText.getText().toString();
                TextView editText2 = getView().findViewById(R.id.txtTitle);
                String title = editText2.getText().toString();


                Bundle extras = new Bundle();
                extras.putString("EXTRA_CONTENT",content);

                NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
                //parent.getView().findViewById(R.id.element_id);

                Navigation.findNavController(getView()).navigate(R.id.action_display_template_to_new_entry, extras);


                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}