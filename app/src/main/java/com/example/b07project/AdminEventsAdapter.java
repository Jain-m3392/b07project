package com.example.b07project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminEventsAdapter extends RecyclerView.Adapter<AdminEventsAdapter.MyViewHolder> {

    private final ArrayList<Event> events;
    private final ArrayList<Venue> venues;
    private final Admin admin;

    public AdminEventsAdapter (ArrayList<Event> events, ArrayList<Venue> venues, Admin admin){
        this.events = events;
        this.venues = venues;
        this.admin = admin;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private Event event;
        private final TextView eventName;
        private final TextView eventTime;
        private final TextView eventDate;
        private final TextView eventVenue;
        private final TextView eventSport;
        private final TextView eventCreator;
        private final TextView eventID;

        //TODO add buttons

        public MyViewHolder(final View view){
            super(view);
            eventName = view.findViewById(R.id.adminEventListName);
            eventTime = view.findViewById(R.id.adminEventListTime);
            eventDate = view.findViewById(R.id.adminEventListDate);
            eventVenue = view.findViewById(R.id.adminEventListVenue);
            eventSport = view.findViewById(R.id.adminEventListSport);
            eventCreator = view.findViewById(R.id.adminEventListCreator);
            eventID = view.findViewById(R.id.adminEventID);
            Button edit = view.findViewById(R.id.adminEventListEdit);
            Context context = view.getContext();
            edit.setOnClickListener(v -> {
                Intent intent = new Intent(context, AdminEditEvent.class);
                intent.putExtra("event", event);
                intent.putExtra("admin", admin);
                context.startActivity(intent);
            });
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
        holder.event = e;
        holder.eventTime.setText(time);
        holder.eventDate.setText(e.getDate());
        holder.eventVenue.setText(v.venueName);
        holder.eventSport.setText(e.sportsType);
        holder.eventCreator.setText(e.creator);
        holder.eventID.setText(String.valueOf(e.eventID));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
