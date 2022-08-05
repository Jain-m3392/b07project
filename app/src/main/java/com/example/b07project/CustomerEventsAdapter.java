package com.example.b07project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerEventsAdapter extends RecyclerView.Adapter<CustomerEventsAdapter.MyViewHolder> {

    private ArrayList<Event> events;

    public CustomerEventsAdapter(ArrayList<Event> events){
         this.events = events;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView eventName;
        private TextView eventTime;
        private TextView eventDate;
        private TextView eventVenue;

        public MyViewHolder(final View view){
            super(view);
            eventName =  view.findViewById(R.id.eventListName);
            eventTime = view.findViewById(R.id.eventListTime);
            eventDate = view.findViewById(R.id.eventListDate);
            eventVenue = view.findViewById(R.id.eventListVenue);
        }
    }

    @NonNull
    @Override
    public CustomerEventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomerEventsAdapter.MyViewHolder holder, int position) {
        String name = events.get(position).getName();
        holder.eventName.setText(name);
        String time = events.get(position).getStartTime() + " - " + events.get(position).getEndTime();
        holder.eventTime.setText(time);
        String date = events.get(position).getDate();
        holder.eventDate.setText(date);
        String venue = String.valueOf(events.get(position).getVenueID());
        holder.eventVenue.setText(venue);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
