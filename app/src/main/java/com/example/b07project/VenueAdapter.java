package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VenueAdapter extends ArrayAdapter<Venue> {

    private Context mContext;
    private int mResource;
    public VenueAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Venue> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView VenueName = convertView.findViewById(R.id.venueName);
        TextView VenueLocation = convertView.findViewById(R.id.venueLocation);
        VenueName.setText(getItem(position).getVenueName());
        VenueLocation.setText(getItem(position).getVenueLocation());
        return convertView;
    }
}