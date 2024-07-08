package com.example.travelbooking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class BoardingPassActivity extends AppCompatActivity {
    ViewGroup layoutTickets;
    Button saveTicket;
    ImageButton back;
    int numAdults = 1;
    String from3Letters, from, to3Letters, to, flightNumber, date, time, price;
    String classType, selectedSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_boarding_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        numAdults = intent.getIntExtra("numAdults", 1);
        from3Letters = intent.getStringExtra("from3Letters");
        from = intent.getStringExtra("from");
        to3Letters = intent.getStringExtra("to3Letters");
        to = intent.getStringExtra("to");
        flightNumber = intent.getStringExtra("flightNumber");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        price = intent.getStringExtra("price");
        classType = intent.getStringExtra("class");
        selectedSeats = intent.getStringExtra("seats");


        layoutTickets = findViewById(R.id.layoutTicket_BPass);
        saveTicket = findViewById(R.id.saveTicket_BPass);
        back = findViewById(R.id.back_button_BPass);

        LinearLayout layoutTicket = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutTicket.setOrientation(LinearLayout.VERTICAL);
        layoutTicket.setLayoutParams(params);
        layoutTickets.addView(layoutTicket);
        String[] seats = selectedSeats.split(",");
        for (int i = 0; i < numAdults; i++){
            LayoutInflater inflater = LayoutInflater.from(this);
            View flightView = inflater.inflate(R.layout.ticket_form, layoutTicket, false);
            TextView from3LettersT = flightView.findViewById(R.id.from3Letters_BPass);
            TextView fromT = flightView.findViewById(R.id.fromFull_BPass);
            TextView to3LettersT = flightView.findViewById(R.id.to3Letters_BPass);
            TextView toT = flightView.findViewById(R.id.toFull_BPass);
            TextView dateT = flightView.findViewById(R.id.dateText_BPass);
            TextView timeT = flightView.findViewById(R.id.timeText_BPass);
            TextView flightNameT = flightView.findViewById(R.id.flightName_BPass);
            TextView passengerT = flightView.findViewById(R.id.passengerText_BPass);
            TextView classT = flightView.findViewById(R.id.classText_BPass);
            TextView ticketT = flightView.findViewById(R.id.ticketText_BPass);
            TextView seatT = flightView.findViewById(R.id.seatText_BPass);

            from3LettersT.setText(from3Letters);
            fromT.setText(from);
            to3LettersT.setText(to3Letters);
            toT.setText(to);
            dateT.setText(date);
            timeT.setText(time);
            flightNameT.setText("British Airways Flight " + flightNumber);
            passengerT.setText("1 Adult");
            classT.setText(classType);
            ticketT.setText(price);
            seatT.setText(seats[i]);
            layoutTicket.addView(flightView);
        }

        saveTicket.setOnClickListener(v -> {
            Toast.makeText(this, "Ticket Saved", Toast.LENGTH_SHORT).show();
        });
        back.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
            finish();
        });
    }
}