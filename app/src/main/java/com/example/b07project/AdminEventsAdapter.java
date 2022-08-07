package com.example.b07project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminEventsAdapter extends RecyclerView.Adapter<AdminEventsAdapter.MyViewHolder> {

    private ArrayList<Event> events;
    private ArrayList<Venue> venues;

    public AdminEventsAdapter (ArrayList<Event> events, ArrayList<Venue> venues){
        this.events = events;
        this.venues = venues;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView eventName;
        private TextView eventTime;
        private TextView eventDate;
        private TextView eventVenue;
        private TextView eventSport;
        private TextView eventCreator;

        //TODO add buttons

        public MyViewHolder(final View view){
            super(view);
            eventName = view.findViewById(R.id.adminEventListName);
            eventTime = view.findViewById(R.id.adminEventListTime);
            eventDate = view.findViewById(R.id.adminEventListDate);
            eventVenue = view.findViewById(R.id.adminEventListVenue);
            eventSport = view.findViewById(R.id.adminEventListSport);
            eventCreator = view.findViewById(R.id.adminEventListCreator);
        }
    }

    @NonNull
    @Override
    public AdminEventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_event_list_item, parent, false);
        return new AdminEventsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminEventsAdapter.MyViewHolder holder, int position) {
        Event e = events.get(position);
        Venue v = venues.get(e.venueID);
        holder.eventName.setText(e.getName());
        String time = e.getStartTime() + " - " + e.getEndTime();
        holder.eventTime.setText(time);
        holder.eventDate.setText(e.getDate());
        holder.eventVenue.setText(v.venueName);
        holder.eventSport.setText(e.sportsType);
        holder.eventCreator.setText(e.creator);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
