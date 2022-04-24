package com.example.tripplanner.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tripplanner.Models.DataObject;
import com.example.tripplanner.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *
 * @author Dennis Finch Imbaya S1903332
 *
 * */

public class RoadworksDetailsActivity extends AppCompatActivity {
    DataObject item;
    private CardView toolBar;
    private ImageView backBtn;
    private CardView locationBtn;
    private TextView status, description, guid, link, publishDate, title, reference, road, region, county, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadworks_details);

        initializeVariables();

        item = (DataObject) getIntent().getSerializableExtra("singleDataObject");

        String scheduleREGEX = "([a-zA-Z0\\s]*between\\s(\\d{2}:\\d{2})\\sand\\s(\\d{2}:\\d{2})\\sfrom\\s(\\d{1,2}\\s[a-zA-Z]*\\s\\d{4})\\sto\\s(\\d{1,2}\\s[a-zA-Z]*\\s\\d{4})|(From\\s(\\d{2}:\\d{2})\\son\\s(\\d{1,2}\\s[\\w]*\\s\\d{4})\\sto\\s(\\d{2}:\\d{2})\\son\\s(\\d{1,2}\\s[\\w]*\\s\\d{4})))";
        String lanesClosedRegex = "(Lane\\sClosures\\s:\\s([a-zA-Z0-9\\s,]*)|Lanes\\sClosed\\s:\\s([a-zA-Z\\s]*))";
        String reasonREGEX = "(Reason\\s:\\s([a-zA-Z\\s]*))";
        String statusREGEX = "(Status\\s:\\s([a-zA-Z\\s]*))";

        Pattern schedulePattern = Pattern.compile(scheduleREGEX);
        Matcher scheduleMatcher = schedulePattern.matcher(item.getDescription());

        Pattern lanesClosedPattern = Pattern.compile(lanesClosedRegex);
        Matcher lanesClosedMatcher = lanesClosedPattern.matcher(item.getDescription());

        Pattern reasonsPattern = Pattern.compile(reasonREGEX);
        Matcher reasonsMatcher = reasonsPattern.matcher(item.getDescription());

        Pattern statusPattern = Pattern.compile(statusREGEX);
        Matcher statusMatcher = statusPattern.matcher(item.getDescription());

        while (scheduleMatcher.find()) {
              Log.d("Schedule", scheduleMatcher.group());
        }

        while (lanesClosedMatcher.find()) {
            Log.d("Schedule", lanesClosedMatcher.group());
        }

        while (reasonsMatcher.find()) {
            Log.d("Schedule", reasonsMatcher.group());
        }

        while (statusMatcher.find()) {
            Log.d("Schedule", statusMatcher.group());
        }

        if (item != null) {
            title.setText(item.getTitle());
            description.setText("Description\n\n " + item.getDescription());
            link.setText("Link: " + item.getLink());
            publishDate.setText(item.getPublishDate());
            road.setText("Road: " + item.getRoad());
            region.setText("Region: " + item.getRegion());
            county.setText("County: " + item.getCounty());
            latitude.setText("Latitude: " + item.getLatitude());
            longitude.setText("Longitude: " + item.getLongitude());
            status.setText("Status: " + item.getStatus());
        } else {

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
                Intent intent = new Intent(RoadworksDetailsActivity.this, MapLocationActivity.class);
                intent.putExtra("lat", item.getLatitude());
                intent.putExtra("long", item.getLongitude());
                startActivity(intent);
            }
        });


    }

    private void initializeVariables() {
        toolBar = findViewById(R.id.topbar);
        toolBar.setBackgroundResource(R.drawable.app_top_bar);

        backBtn = findViewById(R.id.back_btn);
        title = findViewById(R.id.title);
        road = findViewById(R.id.road);
        description = findViewById(R.id.description);
        status = findViewById(R.id.status);
        region = findViewById(R.id.region);
        county = findViewById(R.id.county);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        publishDate = findViewById(R.id.publish_date);
        link = findViewById(R.id.link);
        locationBtn = findViewById(R.id.location_btn);
    }
}