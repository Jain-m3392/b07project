package com.example.b07project;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminHeaderAdapter extends RecyclerView.Adapter<AdminHeaderAdapter.MyViewHolder> {

    String text;

    public AdminHeaderAdapter(String text){
        this.text = text;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private Button filter;

        public MyViewHolder(final View view){
            super(view);
            title = view.findViewById(R.id.adminEventHeader);
            title.setTypeface(null, Typeface.BOLD);
            title.setTextSize(20);
            filter = view.findViewById(R.id.adminFilterEvents);
        }

    }

    @NonNull
    @Override
    public AdminHeaderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_event_list_header, parent, false);
        return new AdminHeaderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHeaderAdapter.MyViewHolder holder, int position) {
        holder.title.setText(text);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
