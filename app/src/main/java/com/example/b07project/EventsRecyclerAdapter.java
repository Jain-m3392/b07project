package com.example.b07project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder> {
    private ArrayList<Event> eventList;
    private ArrayList<Venue> venueList;
    private Customer customer;

    public EventsRecyclerAdapter(ArrayList<Event> eventList, ArrayList<Venue> venueList, Customer customer) {
        this.eventList = eventList;
        this.venueList = venueList;
        this.customer = customer;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Event event;
        private TextView eventName;
        private TextView venueName;
        private TextView venueAddress;
        private TextView capacity;
        private TextView startTime;
        private TextView endTime;
        private Button join;
        private DatabaseReference newEventParticipantRef;
        private DatabaseReference newJoinedEventRef;

        public ViewHolder(final View view){
            super(view);
            this.eventName = view.findViewById(R.id.eventName);
            this.venueName = view.findViewById(R.id.venueName);
            this.venueAddress = view.findViewById(R.id.address);
            this.capacity = view.findViewById(R.id.capacity);
            this.startTime = view.findViewById(R.id.startTime);
            this.endTime = view.findViewById(R.id.endTime);
            this.join = view.findViewById(R.id.joinEvent);

            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    join.setEnabled(false);
                    join.setText("Joined!");
                }
            });
        }

    }

    @NonNull
    @Override
    public EventsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_information, parent, false);
        return new ViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsRecyclerAdapter.ViewHolder holder, int position) {
        Event event = eventList.get(position);
        Venue venue = venueList.get(event.venueID);
        //initialize basic values
        holder.eventName.setText(event.name);
        holder.venueName.setText(venue.venueName);
        holder.venueAddress.setText(venue.venueLocation);
        holder.capacity.setText(String.valueOf(event.capacity));
        holder.startTime.setText(event.startTime);
        holder.endTime.setText(event.endTime);
        holder.event = event;

        //logic for allowing to join events
        holder.join.setText(event.customers.size() < event.capacity ? "Join" : "Event full");
        holder.join.setEnabled(event.customers.size() < event.capacity ? true : false);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
