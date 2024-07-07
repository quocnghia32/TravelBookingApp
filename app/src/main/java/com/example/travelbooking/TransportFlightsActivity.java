package com.example.travelbooking;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransportFlightsActivity extends AppCompatActivity {

    private TextView numFlights;
    private WeeklyView weeklyView;
    private List<Event> events;
    private String selectedDate = "15";
    private HorizontalScrollView scrollView;

    private ArrayList<Flight> mFlights ;
    private RecyclerView mRecyclerFlight;
    private FlightAdapter mFlightAdapter;

    private MyDatabaseHelper db;
    private int year, month, day;

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
        selectedDate = String.valueOf(day);
        if (day < 10) selectedDate = "0" + selectedDate;

        ImageButton back = findViewById(R.id.back_button_flights);
        back.setOnClickListener(v -> finish());
        scrollView = findViewById(R.id.horizontalScrollView);
        weeklyView = findViewById(R.id.weeklyView);
        numFlights = findViewById(R.id.numFlight);
        mRecyclerFlight = findViewById(R.id.flights_recycler_view);
        mFlights = new ArrayList<>();
        db = new MyDatabaseHelper(this);

        weeklyView.setMonthYear(year, month);

        weeklyView.setOnDaySelectedListener(date -> setListFlight(date));

        int offset = weeklyView.setSelectedDate(selectedDate);
        scrollView.post(() -> scrollView.scrollTo(offset, 0));

        setListFlight(selectedDate);
        mFlightAdapter = new FlightAdapter(this,mFlights);
        mRecyclerFlight.setAdapter(mFlightAdapter);
        mRecyclerFlight.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListFlight(String date) {
        Cursor cursor = db.readFlightsByDate(year, month, date);
        mFlights.clear();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
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
        numFlights.setText(mFlights.size() + " flights");
        mFlightAdapter = new FlightAdapter(this,mFlights);
        mRecyclerFlight.setAdapter(mFlightAdapter);
        mRecyclerFlight.setLayoutManager(new LinearLayoutManager(this));
    }
}
