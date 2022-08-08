package com.example.b07project;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Venue implements Parcelable, Pushable {

    public ArrayList<Integer> events;
    public int venueID;
    public String venueLocation;
    public String venueName;
    public ArrayList<String> accessibleSports;

    public Venue(){} //Empty constructor for Firebase to work

    public Venue(int venueID, String venueName, String venueLocation, ArrayList<Integer> events, ArrayList<String> accessibleSports){
        this.events = events;
        this.venueID = venueID;
        this.venueLocation = venueLocation;
        this.venueName = venueName;
        this.accessibleSports = accessibleSports;
    }

    protected Venue(Parcel in) {

        events = in.readArrayList(null);
        venueID = in.readInt();
        venueLocation = in.readString();
        venueName = in.readString();
        accessibleSports = in.readArrayList(null);
    }

    public static final Creator<Venue> CREATOR = new Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };

    public ArrayList<Event> fetchEvents(){
        ArrayList<Event> res = new ArrayList<Event>();
        for (Event e: User.fetchAllEvents()){
            if (e.venueID == this.venueID){
                res.add(e);
            }
        }
        return res;
    }
    
    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (!(o instanceof Venue)){
            return false;
        }
        Venue v = (Venue)o;
        return v.venueID == this.venueID;
    }

    public String getVenueLocation() {
        return venueLocation;
    }

    public void setVenueLocation(String venueLocation) {
        this.venueLocation = venueLocation;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(events);
        parcel.writeInt(venueID);
        parcel.writeString(venueLocation);
        parcel.writeString(venueName);
        parcel.writeList(accessibleSports);
    }

    @Override
    public void push() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("venues");
        ref.child(String.valueOf(this.venueID)).setValue(this);

    }
}
