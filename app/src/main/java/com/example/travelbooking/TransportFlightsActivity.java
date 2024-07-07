package com.example.travelbooking;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransportFlightsActivity extends AppCompatActivity {

    private TextView numFlights;
    private WeeklyView weeklyView;
    private List<Event> events;
    private String selectedDate = "15";
    String from3Letters, fromFull, to3Letters, toFull;
    private HorizontalScrollView scrollView;

    private ArrayList<Flight> mFlights;
    private RecyclerView mRecyclerFlight;
    private FlightAdapter mFlightAdapter;

    private MyDatabaseHelper db;
    private int year, month, day;
    ImageButton filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transport_flights);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        year = intent.getIntExtra("fromDateYear", 0);
        month = intent.getIntExtra("fromDateMonth", 0) + 1;
        day = intent.getIntExtra("fromDateDay", 0);
        String curLocation = intent.getStringExtra("from");
        from3Letters = curLocation.substring(curLocation.indexOf("(") + 1, curLocation.indexOf(")"));
        fromFull = curLocation.substring(0, curLocation.indexOf("(") - 1);
        String destLocation = intent.getStringExtra("to");
        to3Letters = destLocation.substring(destLocation.indexOf("(") + 1, destLocation.indexOf(")"));
        toFull = destLocation.substring(0, destLocation.indexOf("(") - 1);
        selectedDate = String.valueOf(day);
        if (day < 10) selectedDate = "0" + selectedDate;

        ImageButton back = findViewById(R.id.back_button_flights);
        back.setOnClickListener(v -> finish());
        scrollView = findViewById(R.id.horizontalScrollView);
        weeklyView = findViewById(R.id.weeklyView);
        numFlights = findViewById(R.id.numFlight);
        mRecyclerFlight = findViewById(R.id.flights_recycler_view);
        filterButton = findViewById(R.id.filter);
        mFlights = new ArrayList<>();
        db = new MyDatabaseHelper(this);

        weeklyView.setMonthYear(year, month);

        weeklyView.setOnDaySelectedListener(date -> setListFlight(date, from3Letters, to3Letters));

        int offset = weeklyView.setSelectedDate(selectedDate);
        scrollView.post(() -> scrollView.scrollTo(offset, 0));

        setListFlight(selectedDate, from3Letters, to3Letters);
        mFlightAdapter = new FlightAdapter(this,mFlights);
        mRecyclerFlight.setAdapter(mFlightAdapter);
        mRecyclerFlight.setLayoutManager(new LinearLayoutManager(this));

        filterButton.setOnClickListener(v -> {
            Fragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFlight, homeFragment).commit();
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setListFlight(String date, String from, String to) {
        Log.i("TransportFlightsActivity111", "setListFlight()");
        Cursor cursor = db.readFlights(year, month, date, from, to);
        mFlights.clear();
        if(cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                String flight_id = cursor.getString(0);
                String flight_from = cursor.getString(1);
                String flight_3lfrom = cursor.getString(2);
                String flight_to = cursor.getString(3);
                String flight_3lto = cursor.getString(4);
                String flight_date = cursor.getString(5);
                String flight_time = cursor.getString(6);
                String flight_price = cursor.getString(7);
                mFlights.add(new Flight(flight_id, flight_from, flight_3lfrom, flight_to, flight_3lto, flight_date, flight_time, flight_price));
            }
        }
        numFlights.setText(mFlights.size() + " flights available " + fromFull + " to " + toFull);
        mFlightAdapter = new FlightAdapter(this,mFlights);
        mRecyclerFlight.setAdapter(mFlightAdapter);
        mRecyclerFlight.setLayoutManager(new LinearLayoutManager(this));
    }
}
