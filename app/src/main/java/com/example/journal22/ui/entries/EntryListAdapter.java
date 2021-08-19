package com.example.journal22.ui.entries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journal22.R;
import com.example.journal22.data.entity.Entry;


public class EntryListAdapter extends ListAdapter<Entry, EntryListAdapter.EntryViewHolder> {
  //  private static ListItemClickListener mOnClickListener;
  //  private static ListItemLongClickListener mOnLongClickListener;
/*
    interface ListItemClickListener{
        void onListItemClick(int position);

       // boolean onListItemLongClick(View view);
    }
    interface ListItemLongClickListener{
        void onListItemLongClick(int position);

    }
*/


    public EntryListAdapter(@NonNull DiffUtil.ItemCallback<Entry> diffCallback) {//, ListItemClickListener onClickListener) {
        super(diffCallback);
        //this.mOnClickListener = onClickListener;
        //this.mOnLongClickListener = OnLongClickListener;

    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return EntryViewHolder.create(parent);
    }


    public static class EntryViewHolder extends RecyclerView.ViewHolder{// implements View.OnClickListener{
        //private final TextView wordItemView;
        private TextView txtWeekDay, txtTitle, txtContent, txtDate,txtDay;

        private EntryViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtName);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtWeekDay = itemView.findViewById(R.id.txtWeekDay);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDay = itemView.findViewById(R.id.txtDay);

           // itemView.setOnClickListener(this);

        }

        public void bind(Entry entry) {
            txtTitle.setText(entry.getTitle());
            txtContent.setText(entry.getContent());
            txtDate.setText(entry.getTime());
            txtWeekDay.setText(entry.getWeekDay());
            txtDay.setText(entry.getDay());

        }

        static EntryViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.entry_row, parent, false);
            return new EntryViewHolder(view);
        }
/*
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
           // mOnClickListener.onListItemClick(position);
            Log.v("TAG", "Clicked it mate");

        }
*/


    }



    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        Entry current = getItem(position);
        holder.bind(current);
    }

    public static class WordDiff extends DiffUtil.ItemCallback<Entry> {

        @Override
        public boolean areItemsTheSame(@NonNull Entry oldItem, @NonNull Entry newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Entry oldItem, @NonNull Entry newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }


}
