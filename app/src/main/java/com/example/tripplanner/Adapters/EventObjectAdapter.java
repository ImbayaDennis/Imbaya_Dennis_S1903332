package com.example.tripplanner.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.Activities.EventDetailsActivity;
import com.example.tripplanner.Models.EventObject;
import com.example.tripplanner.R;

import java.util.List;

public class EventObjectAdapter extends RecyclerView.Adapter<EventObjectAdapter.NewViewHolder> {

    List<EventObject> eventObjectList;
    Activity context;

    public EventObjectAdapter(List<EventObject> eventObjectList, Activity context) {
        this.eventObjectList = eventObjectList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_event_item, parent, false);

        return new NewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder newViewHolder, int position) {

        EventObject item = eventObjectList.get(position);

        newViewHolder.date.setText(item.getDate());
        newViewHolder.title.setText(item.getTitle());
        newViewHolder.desc.setText(item.getDescription());
        newViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, EventDetailsActivity.class);
                    intent.putExtra("singleEventObject", item);
                    context.startActivity(intent);
                }catch (Exception e){
                    Log.d("Error", e.toString());
                }
            }
        });

        newViewHolder.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, EventDetailsActivity.class);
                    intent.putExtra("details", item);
                    context.startActivity(intent);
                }catch (Exception e){
                    Log.d("Error", e.toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventObjectList.size();

    }

    public class NewViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;
        TextView title, desc, date;
        ImageView direction;

        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.event_parent_layout);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.publish_date);
            direction = itemView.findViewById(R.id.scheduler_icon);
        }
    }
}
