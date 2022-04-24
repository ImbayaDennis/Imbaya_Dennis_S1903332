package com.example.tripplanner.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tripplanner.Adapters.DataObjectAdapter;
import com.example.tripplanner.Models.DataObject;
import com.example.tripplanner.R;
import com.example.tripplanner.XmlPullParserHandler;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.paperdb.Paper;

/*
 *
 * @author Dennis Finch Imbaya S1903332
 *
 * */

public class MainActivity extends AppCompatActivity {

    private static DataObjectAdapter mAdapter;
    private static RecyclerView recyclerView;
    private static ArrayList<DataObject> dataObjectArrayList;
    private CardView toolBar, schedulerBtn;
    private TextView noDataFound;
    private TextView dateTv;
    private ImageView filtersBtn;
    private EditText searchInput;
    private LinearLayout dateLayout;
    private ImageView closeBtn;
    private ShimmerFrameLayout shimmerFrameLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static String getDate(long milliSeconds, String dateFormat) {
        // Creating a DateFormatter object to display date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Creating a calendar object that will convert the date and time value in milliseconds to an actual date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(MainActivity.this);
        initializeVariables();

//        parserXMLData();

        FetchRSSFeedData downloadingTask = new FetchRSSFeedData();
        downloadingTask.execute();

        SearchInBackground backgroundSearch = new SearchInBackground();
        backgroundSearch.execute();

        filtersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterByDate();
            }
        });
        schedulerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventSchedulerActivity.class));
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateLayout.setVisibility(View.GONE);

                if (dataObjectArrayList.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    noDataFound.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    noDataFound.setVisibility(View.VISIBLE);
                }

                mAdapter = new DataObjectAdapter(dataObjectArrayList, MainActivity.this);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataObjectArrayList = null;
                recyclerView.setVisibility(View.GONE);
                FetchRSSFeedData downloadingTask = new FetchRSSFeedData();
                downloadingTask.execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Landscape Mode", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "Portrait Mode", Toast.LENGTH_SHORT).show();
        }
    }

    public void filterByDate() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                long startDateMilliseconds = cal.getTimeInMillis();
                Log.d("selectTimeInmilliseconds", startDateMilliseconds + "");

                String dateString = getDate(startDateMilliseconds, "EE, dd MMM yyyy");
                dateTv.setText(dateString);
                dateLayout.setVisibility(View.VISIBLE);

                ArrayList<DataObject> searchResultArray = new ArrayList<>();
                for (DataObject element : dataObjectArrayList) {
                    if (element.getPublishDate().contains(dateString)) {
                        searchResultArray.add(element);
                    }
                }
                if (searchResultArray.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    noDataFound.setVisibility(View.VISIBLE);
                }

                mAdapter = new DataObjectAdapter(searchResultArray, MainActivity.this);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();


            }
        }, yy, mm, dd);
        datePicker.show();
    }


    private void initializeVariables() {
        toolBar = findViewById(R.id.topbar);
        toolBar.setBackgroundResource(R.drawable.app_top_bar);
        dataObjectArrayList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        recyclerView = findViewById(R.id.data_list);
        shimmerFrameLayout = findViewById(R.id.shimmer_loader);
        noDataFound = findViewById(R.id.no_data_result);
        searchInput = findViewById(R.id.search_input);
        filtersBtn = findViewById(R.id.filters_btn);
        schedulerBtn = findViewById(R.id.scheduler_btn);
        dateLayout = findViewById(R.id.filtered_date_section);
        dateTv = findViewById(R.id.filter_text);
        closeBtn = findViewById(R.id.close_btn);
    }

    public class SearchInBackground extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            searchInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().trim().length() == 0) {
                        if (dataObjectArrayList.size() != 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            noDataFound.setVisibility(View.GONE);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            noDataFound.setVisibility(View.VISIBLE);
                        }

                        mAdapter = new DataObjectAdapter(dataObjectArrayList, MainActivity.this);
                    } else {
                        ArrayList<DataObject> clone = new ArrayList<>();
                        for (DataObject element : dataObjectArrayList) {
                            if (element.getRoad().toLowerCase().contains(s.toString().toLowerCase())) {
                                clone.add(element);
                            }
                        }
                        if (clone.size() != 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            noDataFound.setVisibility(View.GONE);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            noDataFound.setVisibility(View.VISIBLE);
                        }

                        mAdapter = new DataObjectAdapter(clone, MainActivity.this);
                    }
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return "";
        }
    }

    public class FetchRSSFeedData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            shimmerFrameLayout.startShimmer();
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            noDataFound.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            // Initiates API call in background and stores the response in current variable
            try {
                URL url = new URL("http://m.highwaysengland.co.uk/feeds/rss/AllEvents.xml");
                URLConnection conn = url.openConnection();
                XmlPullParserHandler parser = new XmlPullParserHandler();
                InputStream inputStream = conn.getInputStream();
                dataObjectArrayList = (ArrayList<DataObject>) parser.parse(inputStream);

            } catch (Exception e) {
                dataObjectArrayList = new ArrayList<DataObject>();
                Log.d("Error", e.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {

            shimmerFrameLayout.setVisibility(View.GONE);
            if (dataObjectArrayList.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFound.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                noDataFound.setVisibility(View.VISIBLE);
            }

            Paper.book().write("dataObject", dataObjectArrayList);
            mAdapter = new DataObjectAdapter(dataObjectArrayList, MainActivity.this);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

    }
}