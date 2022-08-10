package com.example.b07project;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopHeaderAdapter extends RecyclerView.Adapter<PopHeaderAdapter.MyViewHolder> {

    public PopHeaderAdapter(){}

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(final View view){
            super(view);
            TextView title = view.findViewById(R.id.selectVenuesTitle);
            title.setTextSize(20);
            title.setTypeface(null, Typeface.BOLD);
        }

    }

    @NonNull
    @Override
    public PopHeaderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_pop_header, parent, false);
        return new PopHeaderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopHeaderAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
