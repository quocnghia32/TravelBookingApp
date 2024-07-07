package com.example.travelbooking;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class TransportFlightsActivity extends AppCompatActivity {

    private TextView selectedDayEvents;
    private WeeklyView weeklyView;
    private List<Event> events;
    private String selectedDate = "15";
    private HorizontalScrollView scrollView;

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
        int year = intent.getIntExtra("fromDateYear", 0);
        int month = intent.getIntExtra("fromDateMonth", 0) + 1;
        int day = intent.getIntExtra("fromDateDay", 0);
        Toast.makeText(this, "Selected Date: " + day + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
        selectedDate = String.valueOf(day);
        if (day < 10) selectedDate = "0" + selectedDate;
        Toast.makeText(this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();

        ImageButton back = findViewById(R.id.back_button_flights);
        back.setOnClickListener(v -> finish());
        scrollView = findViewById(R.id.horizontalScrollView);
        weeklyView = findViewById(R.id.weeklyView);
        selectedDayEvents = findViewById(R.id.selectedDayEvents);
        events = new ArrayList<>();

        events.add(new Event("2024-07-01", "Event 1"));
        events.add(new Event("2024-07-03", "Event 2"));
        events.add(new Event("2024-07-05", "Event 3"));

        weeklyView.setEvents(events);
        weeklyView.setMonthYear(year, month); // Set the desired month and year

        weeklyView.setOnDaySelectedListener(date -> showEventsForDate(date));

        int offset = weeklyView.setSelectedDate(selectedDate);
        scrollView.post(() -> scrollView.scrollTo(offset, 0));
    }

    private void showEventsForDate(String date) {
        StringBuilder eventsForDate = new StringBuilder("Events for " + date + ":\n");
        for (Event event : events) {
            if (event.getDate().equals(date)) {
                eventsForDate.append(event.getTitle()).append("\n");
            }
        }
        selectedDayEvents.setText(eventsForDate.toString());
    }
}
