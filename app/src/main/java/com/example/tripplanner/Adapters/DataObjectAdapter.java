package com.example.tripplanner.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.Activities.RoadworksDetailsActivity;
import com.example.tripplanner.Models.DataObject;
import com.example.tripplanner.R;

import java.util.List;

public class DataObjectAdapter extends RecyclerView.Adapter<DataObjectAdapter.NewViewHolder> {

    List<DataObject> dataObjectList;
    Activity context;

    public DataObjectAdapter(List<DataObject> dataObjects, Activity context) {
        this.dataObjectList = dataObjects;
        this.context = context;
    }

    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_data_item, parent, false);

        return new NewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder newViewHolder, int position) {

        DataObject item = dataObjectList.get(position);

        newViewHolder.road.setText("Road: " + item.getRoad());
        newViewHolder.region.setText("Region : " + item.getRegion());
        newViewHolder.status.setText("Status : " + item.getStatus());
        newViewHolder.date.setText(item.getPublishDate());
        newViewHolder.title.setText(item.getTitle());
        newViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RoadworksDetailsActivity.class);
                intent.putExtra("singleDataObject", item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataObjectList.size();

    }

    public class NewViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;
        TextView title, date, region, road, status;

        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.data_parent_layout);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.publish_date);
            road = itemView.findViewById(R.id.road_tv);
            region = itemView.findViewById(R.id.region_tv);
            status = itemView.findViewById(R.id.status);
        }
    }
}
