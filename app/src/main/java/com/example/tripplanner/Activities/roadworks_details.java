package com.example.tripplanner.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tripplanner.Models.DataObject;
import com.example.tripplanner.R;

public class roadworks_details extends AppCompatActivity {
    DataObject item;
    private CardView toolBar;
    private ImageView backBtn;
    private LinearLayout locationBtn;
    private TextView  status, description, guid, link, publishDate, title, reference, road, region, county, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadworks_details);

        initializeVariables();

        item = (DataObject) getIntent().getSerializableExtra("details");
        if (item != null) {
            title.setText(item.getTitle());
            description.setText("Description: " + item.getDescription());
            guid.setText("Guid: " + item.getGuid());
            link.setText("Link: " + item.getLink());
            publishDate.setText(item.getPublishDate());
            reference.setText("Reference: " + item.getReference());
            road.setText("Road: " + item.getRoad());
            region.setText("Region: " + item.getRegion());
            county.setText("County: " + item.getCounty());
            latitude.setText("Latitude: " + item.getLatitude());
            longitude.setText("Longitude: " + item.getLongitude());
            status.setText("Status: " + item.getStatus());
        }else {

        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(item.getLink()); // missing 'http://' will cause crash
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(roadworks_details.this, location_activity.class);
                intent.putExtra("lat", item.getLatitude());
                intent.putExtra("long", item.getLongitude());
                startActivity(intent);
            }
        });


    }

    private void initializeVariables() {
        toolBar = findViewById(R.id.topbar);
        toolBar.setBackgroundResource(R.drawable.bottom_corer_round);

        locationBtn = findViewById(R.id.location_btn);
        backBtn = findViewById(R.id.back_btn);
        status = findViewById(R.id.status);
        description = findViewById(R.id.description);
        guid = findViewById(R.id.guid);
        link = findViewById(R.id.link);
        publishDate = findViewById(R.id.date_tv);
        title = findViewById(R.id.title);
        reference = findViewById(R.id.reference);
        road = findViewById(R.id.road);
        region = findViewById(R.id.region);
        county = findViewById(R.id.county);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
    }
}