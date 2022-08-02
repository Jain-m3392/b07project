package com.example.b07project;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

public class NavBar {
    public boolean navigate(@NonNull MenuItem item, Context context, Customer customer){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menuitem_account:
                intent = new Intent(context, AccountActivity.class);
                intent.putExtra("Customer", customer);
                context.startActivity(intent);
                return true;
            case R.id.menuitem_home:
                intent = new Intent(context, CustomerEventsViewActivity.class);
                intent.putExtra("Customer", customer);
                context.startActivity(intent);
                return true;
            case R.id.menuitem_venues:
                intent = new Intent(context, CustomerVenuesView.class);
                intent.putExtra("Customer", customer);
                context.startActivity(intent);
                return true;
            case R.id.menuitem_events:
                //TODO
                return true;
        }
        return false;
    }
}
