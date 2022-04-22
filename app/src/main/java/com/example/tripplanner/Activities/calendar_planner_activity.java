package com.example.tripplanner.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.Adapters.EventObjectAdapter;
import com.example.tripplanner.Models.EventObject;
import com.example.tripplanner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.paperdb.Paper;

public class calendar_planner_activity extends AppCompatActivity {
    private CardView toolBar, plannerBtn;
    private ImageView backBtn;

    private EventObjectAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<EventObject> dataItemArrayList;
    private TextView noDataFound;

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        initializeVariables();
        Paper.init(calendar_planner_activity.this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        plannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(calendar_planner_activity.this, R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(calendar_planner_activity.this).inflate(R.layout.dialog_box, viewGroup, false);

                TextView saveBtn = dialogView.findViewById(R.id.transfer_btn);
                TextView cancelBtn = dialogView.findViewById(R.id.cancel_btn);
                TextView titleEt = dialogView.findViewById(R.id.title_et);
                TextView descEt = dialogView.findViewById(R.id.description_et);
                TextView dateTv = dialogView.findViewById(R.id.date_tv);


                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                        String title = titleEt.getText().toString().trim();
                        String desc = descEt.getText().toString().trim();
                        String date = dateTv.getText().toString().trim();
                        if (TextUtils.isEmpty(title)) {
                            titleEt.setError("Enter title");
                            titleEt.requestFocus();
                        } else if (TextUtils.isEmpty(desc)) {
                            descEt.setError("Enter description");
                            descEt.requestFocus();
                        } else if (TextUtils.isEmpty(date)) {
                            Toast.makeText(calendar_planner_activity.this, "Select date..", Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayList<EventObject> eventObjectArrayList = Paper.book().read("eventsList", new ArrayList<>());
                            EventObject eventObject = new EventObject(title, desc, date);
                            eventObjectArrayList.add(eventObject);
                            Log.d("sizeOfList", eventObjectArrayList.size() + "");
                            Paper.book().write("eventsList", eventObjectArrayList);

                            if (eventObjectArrayList.size() != 0) {
                                recyclerView.setVisibility(View.VISIBLE);
                                noDataFound.setVisibility(View.GONE);
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                noDataFound.setVisibility(View.VISIBLE);
                            }

                            mAdapter = new EventObjectAdapter(eventObjectArrayList, calendar_planner_activity.this);
                            recyclerView.setNestedScrollingEnabled(false);
                            recyclerView.setLayoutManager(new LinearLayoutManager(calendar_planner_activity.this));
                            recyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });

                dateTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar calendar = Calendar.getInstance();
                        int yy = calendar.get(Calendar.YEAR);
                        int mm = calendar.get(Calendar.MONTH);
                        int dd = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePicker = new DatePickerDialog(calendar_planner_activity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String date = dayOfMonth + "." + (monthOfYear + 1)
                                        + "." + year;
                                Calendar cal = Calendar.getInstance();
                                cal.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                                cal.set(Calendar.MONTH, view.getMonth());
                                cal.set(Calendar.YEAR, view.getYear());
                                cal.set(Calendar.HOUR_OF_DAY, 0);
                                cal.set(Calendar.MINUTE, 0);
                                cal.set(Calendar.SECOND, 0);

                                long startDateInMili = cal.getTimeInMillis();
                                Log.d("selectTimeInmili", startDateInMili + "");
                                String dateString = getDate(startDateInMili, "EE, dd MMM yyyy");

                                dateTv.setText(dateString);

                            }
                        }, yy, mm, dd);
                        datePicker.show();

                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataItemArrayList = Paper.book().read("eventsList", new ArrayList<>());
        if (dataItemArrayList.size() != 0) {
            recyclerView.setVisibility(View.VISIBLE);
            noDataFound.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            noDataFound.setVisibility(View.VISIBLE);
        }

        mAdapter = new EventObjectAdapter(dataItemArrayList, calendar_planner_activity.this);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(calendar_planner_activity.this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initializeVariables() {
        toolBar = findViewById(R.id.topbar);
        toolBar.setBackgroundResource(R.drawable.bottom_corer_round);
        plannerBtn = findViewById(R.id.planner_btn);
        backBtn = findViewById(R.id.back_btn);
        noDataFound = findViewById(R.id.no_data);

        dataItemArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.data_list);
    }
}