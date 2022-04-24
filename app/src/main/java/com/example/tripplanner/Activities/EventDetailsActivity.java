package com.example.tripplanner.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.Adapters.DataObjectAdapter;
import com.example.tripplanner.Models.DataObject;
import com.example.tripplanner.Models.EventObject;
import com.example.tripplanner.R;

import java.util.ArrayList;

import io.paperdb.Paper;

public class EventDetailsActivity extends AppCompatActivity {
    private static DataObjectAdapter mAdapter;
    EventObject item;
    private CardView toolBar;
    private ImageView backBtn;
    private RecyclerView recyclerView;
    private ArrayList<DataObject> dataObjectArrayList;
    private TextView noDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_roadworks_list);
        item = (EventObject) getIntent().getSerializableExtra("singleEventObject");

        Paper.init(EventDetailsActivity.this);
        initializeVariables();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initializeVariables() {
        toolBar = findViewById(R.id.topbar);
        toolBar.setBackgroundResource(R.drawable.app_top_bar);
        backBtn = findViewById(R.id.back_btn);
        noDataFound = findViewById(R.id.no_data);

        dataObjectArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.data_list);
        ArrayList<DataObject> tempList = Paper.book().read("dataObject", new ArrayList<>());
        int count = 0;
        for (int i = 0; i < tempList.size(); i++) {
            DataObject dateItem = tempList.get(i);
            if (dateItem.getPublishDate().contains(item.getDate())) {
                dataObjectArrayList.add(dateItem);
            }
            count++;
        }

        if (count == tempList.size()) {
            if (dataObjectArrayList.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFound.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
            }

            mAdapter = new DataObjectAdapter(dataObjectArrayList, EventDetailsActivity.this);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(EventDetailsActivity.this));
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

    }
}