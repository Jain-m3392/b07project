package com.example.b07project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private RecyclerView recyclerView;
    private final ArrayList<Venue> selectedVenues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Intent intent = getIntent();
        admin = intent.getParcelableExtra("Admin");
        ArrayList<Venue> venues = admin.fetchCreatedVenues();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_pop);

        getWindow().setLayout(880, 1250);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(layoutParams);

        recyclerView = findViewById(R.id.filterRecyclerView);
        Button apply = findViewById(R.id.applyButton);

        setAdapter(venues);

        apply.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent1 = new Intent(context, AdminEventsView.class);
            intent1.putExtra("Admin", admin);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("Venues", selectedVenues);
            intent1.putExtras(bundle);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(intent1);
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
