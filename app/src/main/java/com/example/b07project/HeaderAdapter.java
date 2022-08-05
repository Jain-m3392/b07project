package com.example.b07project;

import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder> {

    private String text;

    public HeaderAdapter(String text){
        this.text = text;
        Log.i("Success", "header created!");
        Log.i("value", this.text);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        public HeaderViewHolder(final View view){
            super(view);
            title = view.findViewById(R.id.eventViewType);
            title.setTypeface(null, Typeface.BOLD);
            title.setTextSize(20);
        }

    }

    @NonNull
    @Override
    public HeaderAdapter.HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_header, parent, false);
        return new HeaderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderAdapter.HeaderViewHolder holder, int position) {
//        Log.i("hi", "i made it here! " + text);
        holder.title.setText(text);
    }

    @Override
    public int getItemCount() {
        return 1;
    }



}
