package com.example.b07project;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

public class AdminNavBar {
    public boolean navigate(@NonNull MenuItem item, Context context, Admin admin){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menuitem_account:
                intent = new Intent(context, AdminAccountActivity.class);
                intent.putExtra("Admin", admin);
                context.startActivity(intent);
                return true;
            case R.id.menuitem_home:
                intent = new Intent(context, AdminEventsView.class);
                intent.putExtra("Admin", admin);
                context.startActivity(intent);
                return true;
            case R.id.menuitem_venues:
                //TODO
//                intent = new Intent(context, CustomerVenuesView.class);
//                intent.putExtra("Customer", admin);
//                context.startActivity(intent);
                return true;
            case R.id.menuitem_new_venue:
                intent = new Intent(context, adminCreateVenue.class);
                intent.putExtra("Admin", admin);
                context.startActivity(intent);
                return true;
        }
        return false;
    }
}
