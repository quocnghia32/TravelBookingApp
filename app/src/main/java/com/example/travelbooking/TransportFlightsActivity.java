package com.example.travelbooking;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;

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
    String from3Letters, fromFull, to3Letters, toFull;
    private HorizontalScrollView scrollView;

    private ArrayList<Flight> mFlights, totalFlights;
    private RecyclerView mRecyclerFlight;
    private FlightAdapter mFlightAdapter;

    private MyDatabaseHelper db;
    private int year, month, day;
    ImageButton filterButton;
    private static final int GET_RESULT = 2510;

    int lowPrice = 0, highPrice = 300;
    int lowTime = 0, highTime = 24;
    boolean isSortingOnPrice = false;
    String classType;
    int numAdults;



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
        classType = intent.getStringExtra("class");
        numAdults = intent.getIntExtra("numAdults", 1);


        ImageButton back = findViewById(R.id.back_button_Flights);
        back.setOnClickListener(v -> finish());
        scrollView = findViewById(R.id.horizontalScrollView_Flights);
        weeklyView = findViewById(R.id.weeklyView);
        numFlights = findViewById(R.id.numFlight_Flights);
        mRecyclerFlight = findViewById(R.id.flights_recycler_view_Flights);
        filterButton = findViewById(R.id.filter_Flights);
        mFlights = new ArrayList<>();
        totalFlights = new ArrayList<>();
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
            //intent for filter activity to get the result, including lowPrice, highPrice, lowTime, highTime, isSortingOnPrice
            Intent filterIntent = new Intent(this, FilterActivity.class);
            filterIntent.putExtra("lowPrice", lowPrice);
            filterIntent.putExtra("highPrice", highPrice);
            filterIntent.putExtra("lowTime", lowTime);
            filterIntent.putExtra("highTime", highTime);
            filterIntent.putExtra("isSortingOnPrice", isSortingOnPrice);
            startActivityForResult(filterIntent, GET_RESULT);
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_RESULT) {
            if (resultCode == RESULT_OK) {
                lowPrice = data.getIntExtra("lowPrice", 0);
                highPrice = data.getIntExtra("highPrice", 300);
                lowTime = data.getIntExtra("lowTime", 0);
                highTime = data.getIntExtra("highTime", 24);
                isSortingOnPrice = data.getBooleanExtra("isSortingOnPrice", false);
                filterFlight();
                numFlights.setText(mFlights.size() + " flights available " + fromFull + " to " + toFull);
                mFlightAdapter = new FlightAdapter(this,mFlights);
                mRecyclerFlight.setAdapter(mFlightAdapter);
                mRecyclerFlight.setLayoutManager(new LinearLayoutManager(this));
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setListFlight(String date, String from, String to) {
        Log.i("TransportFlightsActivity111", "setListFlight()");
        Cursor cursor = db.readFlights(year, month, date, from, to);
        totalFlights.clear();
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
                totalFlights.add(new Flight(flight_id, flight_from, flight_3lfrom, flight_to, flight_3lto, flight_date, flight_time, flight_price));
            }
        }
        filterFlight();
        numFlights.setText(mFlights.size() + " flights available " + fromFull + " to " + toFull);
        mFlightAdapter = new FlightAdapter(this,mFlights);
        mRecyclerFlight.setAdapter(mFlightAdapter);
        mRecyclerFlight.setLayoutManager(new LinearLayoutManager(this));
    }
    private void filterFlight() {
        mFlights.clear();
        for (Flight flight : totalFlights) {
            int price = Integer.parseInt(flight.getFlightPrice().substring(1));
            if (price < lowPrice || price > highPrice) continue;
            String time = flight.getFlightTime();
            int hour = Integer.parseInt(time.substring(0, time.indexOf(":")));
            if (hour == 12) hour = 0;
            if (time.contains("PM")) hour += 12;
            if (hour < lowTime || hour > highTime) continue;
            mFlights.add(flight);
        }
        if (isSortingOnPrice) {
            mFlights.sort((o1, o2) -> {
                int price1 = Integer.parseInt(o1.getFlightPrice().substring(1));
                int price2 = Integer.parseInt(o2.getFlightPrice().substring(1));
                return price1 - price2;
            });
        } else {
            mFlights.sort((o1, o2) -> {
                String time1 = o1.getFlightTime();
                String time2 = o2.getFlightTime();
                int hour1 = Integer.parseInt(time1.substring(0, time1.indexOf(":")));
                int hour2 = Integer.parseInt(time2.substring(0, time2.indexOf(":")));
                if (hour1 == 12) hour1 = 0;
                if (time1.contains("PM")) hour1 += 12;
                if (hour2 == 12) hour2 = 0;
                if (time2.contains("PM")) hour2 += 12;
                return hour1 - hour2;
            });
        }
    }
}
