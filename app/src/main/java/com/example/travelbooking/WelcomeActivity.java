package com.example.travelbooking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button loginButton = findViewById(R.id.btnLogin);
        Button registerButton = findViewById(R.id.btnSignUp);
        loginButton.setOnClickListener(v -> {
            Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(i);
        });
        registerButton.setOnClickListener(v -> {
            MyDatabaseHelper db = new MyDatabaseHelper(this);
            db.deleteAllData();
            Flight flight1 = new Flight("NL-41", "New York", "NYC", "London", "LDN", "2024-07-02", "9:00 AM", "$50");
            db.addFlight(flight1);

            Flight flight2 = new Flight("AA-101", "Los Angeles", "LAX", "Tokyo", "TYO", "2024-07-05", "12:00 PM", "$120");
            db.addFlight(flight2);

            Flight flight3 = new Flight("BA-309", "Paris", "PAR", "Dubai", "DXB", "2024-07-07", "3:00 PM", "$150");
            db.addFlight(flight3);

            Flight flight4 = new Flight("QF-12", "Sydney", "SYD", "Singapore", "SIN", "2024-07-10", "6:00 AM", "$200");
            db.addFlight(flight4);

            Flight flight5 = new Flight("DL-50", "Atlanta", "ATL", "Johannesburg", "JNB", "2024-07-12", "11:00 AM", "$300");
            db.addFlight(flight5);

            Flight flight6 = new Flight("EK-202", "Dubai", "DXB", "New York", "NYC", "2024-07-15", "8:30 PM", "$400");
            db.addFlight(flight6);

            Flight flight7 = new Flight("LH-401", "Frankfurt", "FRA", "San Francisco", "SFO", "2024-07-18", "10:00 AM", "$350");
            db.addFlight(flight7);

            Flight flight8 = new Flight("AF-007", "Paris", "CDG", "New York", "NYC", "2024-07-20", "2:00 PM", "$450");
            db.addFlight(flight8);

            Flight flight9 = new Flight("CX-888", "Hong Kong", "HKG", "Vancouver", "YVR", "2024-07-22", "5:00 PM", "$500");
            db.addFlight(flight9);

            Flight flight10 = new Flight("SQ-318", "Singapore", "SIN", "London", "LHR", "2024-07-25", "11:00 PM", "$600");
            db.addFlight(flight10);

            Flight flight11 = new Flight("UA-860", "Chicago", "ORD", "Beijing", "PEK", "2024-07-27", "7:00 AM", "$700");
            db.addFlight(flight11);

            Flight flight12 = new Flight("JL-701", "Tokyo", "HND", "Sydney", "SYD", "2024-07-30", "9:30 AM", "$750");
            db.addFlight(flight12);

            Flight flight13 = new Flight("QF-10", "Melbourne", "MEL", "London", "LHR", "2024-08-02", "5:00 PM", "$800");
            db.addFlight(flight13);

            Flight flight14 = new Flight("NZ-101", "Auckland", "AKL", "Los Angeles", "LAX", "2024-08-05", "3:00 PM", "$850");
            db.addFlight(flight14);

            Flight flight15 = new Flight("BA-212", "Boston", "BOS", "London", "LHR", "2024-08-08", "6:00 AM", "$900");
            db.addFlight(flight15);

            Flight flight16 = new Flight("LH-420", "Munich", "MUC", "Boston", "BOS", "2024-08-10", "8:00 AM", "$500");
            db.addFlight(flight16);

            Flight flight17 = new Flight("AC-759", "Toronto", "YYZ", "Los Angeles", "LAX", "2024-08-12", "10:00 PM", "$450");
            db.addFlight(flight17);

            Flight flight18 = new Flight("AI-102", "New Delhi", "DEL", "San Francisco", "SFO", "2024-08-14", "1:00 AM", "$600");
            db.addFlight(flight18);

            Flight flight19 = new Flight("CA-983", "Beijing", "PEK", "New York", "JFK", "2024-08-16", "3:00 PM", "$700");
            db.addFlight(flight19);

            Flight flight20 = new Flight("SQ-221", "Singapore", "SIN", "Sydney", "SYD", "2024-08-18", "5:30 AM", "$400");
            db.addFlight(flight20);

            Flight flight21 = new Flight("CX-845", "New York", "JFK", "Hong Kong", "HKG", "2024-08-20", "8:00 AM", "$500");
            db.addFlight(flight21);

            Flight flight22 = new Flight("JL-62", "Tokyo", "NRT", "Los Angeles", "LAX", "2024-08-22", "10:30 PM", "$450");
            db.addFlight(flight22);

            Flight flight23 = new Flight("AF-256", "Paris", "CDG", "Mumbai", "BOM", "2024-08-24", "9:00 AM", "$300");
            db.addFlight(flight23);

            Flight flight24 = new Flight("EK-203", "Dubai", "DXB", "Los Angeles", "LAX", "2024-08-26", "2:30 AM", "$800");
            db.addFlight(flight24);

            Flight flight25 = new Flight("BA-283", "London", "LHR", "Los Angeles", "LAX", "2024-08-28", "11:00 AM", "$650");
            db.addFlight(flight25);

            Flight flight26 = new Flight("AA-136", "New York", "JFK", "Rio de Janeiro", "GIG", "2024-08-30", "7:00 PM", "$550");
            db.addFlight(flight26);

            Flight flight27 = new Flight("QF-8", "Dallas", "DFW", "Sydney", "SYD", "2024-09-02", "10:00 PM", "$900");
            db.addFlight(flight27);

            Flight flight28 = new Flight("NZ-2", "London", "LHR", "Auckland", "AKL", "2024-09-04", "9:30 PM", "$950");
            db.addFlight(flight28);

            Flight flight29 = new Flight("AF-178", "Paris", "CDG", "Buenos Aires", "EZE", "2024-09-06", "11:00 AM", "$700");
            db.addFlight(flight29);

            Flight flight30 = new Flight("EK-524", "Dubai", "DXB", "Hyderabad", "HYD", "2024-09-08", "4:00 PM", "$400");
            db.addFlight(flight30);

            Flight flight31 = new Flight("LH-762", "Frankfurt", "FRA", "Bangalore", "BLR", "2024-09-10", "1:00 AM", "$600");
            db.addFlight(flight31);

            Flight flight32 = new Flight("UA-851", "San Francisco", "SFO", "Shanghai", "PVG", "2024-09-12", "12:00 PM", "$800");
            db.addFlight(flight32);

            Flight flight33 = new Flight("AC-870", "Toronto", "YYZ", "Paris", "CDG", "2024-09-14", "6:00 PM", "$550");
            db.addFlight(flight33);

            Flight flight34 = new Flight("AI-144", "Mumbai", "BOM", "Newark", "EWR", "2024-09-16", "12:30 AM", "$650");
            db.addFlight(flight34);

            Flight flight35 = new Flight("JL-43", "Tokyo", "NRT", "London", "LHR", "2024-09-18", "3:00 PM", "$750");
            db.addFlight(flight35);

            Flight flight36 = new Flight("SQ-325", "Frankfurt", "FRA", "Singapore", "SIN", "2024-09-20", "10:00 PM", "$850");
            db.addFlight(flight36);

            Flight flight37 = new Flight("DL-3", "Atlanta", "ATL", "Paris", "CDG", "2024-09-22", "5:00 PM", "$500");
            db.addFlight(flight37);

            Flight flight38 = new Flight("QF-2", "London", "LHR", "Sydney", "SYD", "2024-09-24", "9:00 AM", "$950");
            db.addFlight(flight38);

            Flight flight39 = new Flight("AF-380", "Paris", "CDG", "Dubai", "DXB", "2024-09-26", "11:30 PM", "$700");
            db.addFlight(flight39);

            Flight flight40 = new Flight("EK-232", "Washington, D.C.", "IAD", "Dubai", "DXB", "2024-09-28", "10:15 AM", "$600");
            db.addFlight(flight40);


        });

    }

}