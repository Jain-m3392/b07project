package com.example.b07project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FilterPop extends Activity {

    private Admin admin;
    private ArrayList<Venue> venues;
    private RecyclerView recyclerView;
    private ArrayList selectedVenues = new ArrayList();
    private Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Intent intent = getIntent();
        admin = intent.getParcelableExtra("Admin");
        venues = admin.fetchCreatedVenues();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(880, 1250);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(layoutParams);

        recyclerView = findViewById(R.id.filterRecyclerView);
        apply = findViewById(R.id.applyButton);

        setAdapter(venues);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AdminEventsView.class);
                intent.putExtra("Admin", admin);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("Venues", selectedVenues);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
            }
        });

    }

    private void setAdapter(ArrayList<Venue> venues){
        PopHeaderAdapter header = new PopHeaderAdapter();
        PopItemAdapter adapter = new PopItemAdapter(venues, new PopItemAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Venue v) {
                selectedVenues.add(v);
            }

            @Override
            public void onItemUncheck(Venue v) {
                selectedVenues.remove(v);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ConcatAdapter cat = new ConcatAdapter(header, adapter);
        recyclerView.setAdapter(cat);
    }

}
