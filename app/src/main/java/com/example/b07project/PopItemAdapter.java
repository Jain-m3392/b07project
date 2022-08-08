package com.example.b07project;

import android.content.ClipData;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PopItemAdapter extends RecyclerView.Adapter<PopItemAdapter.MyViewHolder> {

    interface OnItemCheckListener{
        void onItemCheck(Venue v);
        void onItemUncheck(Venue v);
    }

    ArrayList<Venue> venues;

    @NonNull
    private OnItemCheckListener onItemClick;

    public PopItemAdapter(ArrayList<Venue> venues, @NonNull OnItemCheckListener onItemCheckListener){
        this.venues = venues;
        this.onItemClick = onItemCheckListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cb;
        private View view;

        public MyViewHolder(final View view){
            super(view);
            this.view = view;
            cb = view.findViewById(R.id.filterCheckbox);
            cb.setClickable(false);
        }

        public void setOnClickListener(View.OnClickListener onClickListener){
            view.setOnClickListener(onClickListener);
        }
    }

    @NonNull
    @Override
    public PopItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_pop_item, parent, false);
        return new PopItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopItemAdapter.MyViewHolder holder, int position) {
        Venue v = venues.get(position);
        holder.cb.setText(v.venueName);
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                holder.cb.setChecked(!(holder.cb.isChecked()));
                if (holder.cb.isChecked()){
                    onItemClick.onItemCheck(v);
                }
                else{
                    onItemClick.onItemUncheck(v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }
}
