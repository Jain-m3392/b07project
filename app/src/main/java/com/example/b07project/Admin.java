package com.example.b07project;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Admin extends User implements Parcelable, Pushable {

    public ArrayList<Integer> venues;

    public Admin(){} //Empty constructor for Firebase loading
    public Admin(String username, ArrayList<Integer> venues){
        this.username = username;
        this.venues = venues;
    }

    public Admin(Parcel in){
        this.username = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.venues = in.readArrayList(null);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeList(venues);
    }

    public static Creator<Admin> CREATOR = new Creator<Admin>() {
        @Override
        public Admin createFromParcel(Parcel parcel) {
            return new Admin(parcel);
        }

        @Override
        public Admin[] newArray(int i) {
            return new Admin[i];
        }
    };



    public ArrayList<Venue> fetchCreatedVenues(){
        ArrayList<Venue> res = new ArrayList<>();
        Log.d("admin", venues.toString());
        for (Venue v: User.fetchAllVenues()){
            Log.d("admin", String.valueOf(v.venueID));
            if (venues.contains(v.venueID)){
                res.add(v);
            }
        }
        return res;
    }

    public void addVenue(Venue v){
        if (!venues.contains(v.venueID)){
            venues.add(v.venueID);

        }
    }


    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        if (!(o instanceof User)){
            return false;
        }
        User a = (User)o;
        return a.username.equals(this.username);
    }

    @Override
    public void push() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("admins");
        ref.child(username).setValue(this);
    }
}
