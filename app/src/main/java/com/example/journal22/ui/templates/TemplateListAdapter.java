package com.example.journal22.ui.templates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journal22.R;
import com.example.journal22.data.Template;

public class TemplateListAdapter extends ListAdapter<Template, TemplateListAdapter.TempViewHolder> {



    public TemplateListAdapter(@NonNull DiffUtil.ItemCallback<Template> diffCallback) {//, ListItemClickListener onClickListener) {
        super(diffCallback);

    }

    @NonNull
    @Override
    public TempViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TempViewHolder.create(parent);
    }


    public static class TempViewHolder extends RecyclerView.ViewHolder{// implements View.OnClickListener{
        private TextView  txtTitle, txtEntry;

        private TempViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtEntry = itemView.findViewById(R.id.txtBody);


        }

        public void bind(Template entry) {
            //    wordItemView.setText(text);

            txtTitle.setText(entry.getTitle());
            txtEntry.setText(entry.getContent());


        }

        static TempViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.template_item, parent, false);
            return new TempViewHolder(view);
        }



    }



    @Override
    public void onBindViewHolder(TempViewHolder holder, int position) {
        Template current = getItem(position);
        holder.bind(current);
    }

    public static class TempDiff extends DiffUtil.ItemCallback<Template> {

        @Override
        public boolean areItemsTheSame(@NonNull Template oldItem, @NonNull Template newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Template oldItem, @NonNull Template newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }





}
