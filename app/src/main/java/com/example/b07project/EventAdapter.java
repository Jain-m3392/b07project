package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {
    private Context mContext;
    private int mResource;

    public EventAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Event> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView EventName = convertView.findViewById(R.id.eventName);
        TextView EventStartEndTime = convertView.findViewById(R.id.eventStartEndTimes);
        TextView EventDate = convertView.findViewById(R.id.eventDate);
        TextView EventCapacity = convertView.findViewById(R.id.eventCapacity);
        EventName.setText(getItem(position).getName());
        EventStartEndTime.setText(getItem(position).getStartTime() + " - " + getItem(position).getEndTime());
        EventDate.setText(getItem(position).getDate());
        EventCapacity.setText(getItem(position).getStringCapacity());

        return convertView;
    }
}