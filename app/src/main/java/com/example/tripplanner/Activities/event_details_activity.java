package com.example.tripplanner.Activities;

import android.os.Bundle;
import android.util.Log;
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

public class event_details_activity extends AppCompatActivity {
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
        setContentView(R.layout.activity_event_list);
        item = (EventObject) getIntent().getSerializableExtra("details");

        Paper.init(event_details_activity.this);
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
        toolBar.setBackgroundResource(R.drawable.bottom_corer_round);
        backBtn = findViewById(R.id.back_btn);
        noDataFound = findViewById(R.id.no_data);

        dataObjectArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.data_list);
        ArrayList<DataObject> tempList = Paper.book().read("datalist", new ArrayList<>());
        int count = 0;
        for (int i = 0; i < tempList.size(); i++) {
            DataObject dateItem = tempList.get(i);
            if (dateItem.getPublishDate().contains(item.getDate())) {
                dataObjectArrayList.add(dateItem);
            }
            count++;
        }
        Log.d("arrayListDetails", count + "");
        Log.d("arrayListDetails", tempList.size() + "");
        if (count == tempList.size()) {
            if (dataObjectArrayList.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFound.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
            }

            mAdapter = new DataObjectAdapter(dataObjectArrayList, event_details_activity.this);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(event_details_activity.this));
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

    }
}