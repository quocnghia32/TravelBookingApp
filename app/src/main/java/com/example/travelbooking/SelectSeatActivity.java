package com.example.travelbooking;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SelectSeatActivity extends AppCompatActivity implements View.OnClickListener {
    ViewGroup layoutSeats, layoutPassengers;
    int seatSize = 100;
    int seatGaping = 15;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_NONE = 0;
    int STATUS_PERSON = 4;
    int NUM_ROW = 16;
    int NUM_COL = 5;
    int CONSTANT = 25102;
    String selectedIds = "";
    ArrayList<String> seatsBooked = new ArrayList<>();
    ArrayList<Integer> seatsSelected = new ArrayList<>();
    ImageButton backButton;
    String flightNumber, from, from3Letters, to, to3Letters, date, time, price, classType;
    int realPrice = 1, numAdults = 1;
    int numSeatChoosen = 0;
    TextView totalSeatText, totalPriceText;
    int curPassenger = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_seat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        flightNumber = getIntent().getStringExtra("flightNumber");
        from = getIntent().getStringExtra("from");
        from3Letters = getIntent().getStringExtra("from3Letters");
        to = getIntent().getStringExtra("to");
        to3Letters = getIntent().getStringExtra("to3Letters");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        price = getIntent().getStringExtra("price");
        realPrice = Integer.parseInt(price.substring(1));
        classType = getIntent().getStringExtra("class");
        numAdults = getIntent().getIntExtra("numAdults", 1);


        layoutSeats = findViewById(R.id.layoutSeat);
        layoutPassengers = findViewById(R.id.layoutPassenger_Seat);
        backButton = findViewById(R.id.back_button_Seat);
        totalSeatText = findViewById(R.id.total_seat_Seat);
        totalPriceText = findViewById(R.id.total_price_Seat);


        for (int i = 0; i < numAdults; i++) seatsSelected.add(-1);
        createPassengersList();
        createSeatsList();

        backButton.setOnClickListener(v -> {
            finish();
        });
    }
    @SuppressLint("ResourceAsColor")
    void createPassengersList(){
        LinearLayout layoutPerson = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutPerson.setOrientation(LinearLayout.HORIZONTAL);
        layoutPerson.setLayoutParams(params);
        layoutPassengers.addView(layoutPerson);

        for (int i = 0; i < numAdults; i++){
            TextView view = new TextView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
            layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
            view.setLayoutParams(layoutParams);
            view.setId(CONSTANT + i+1);
            view.setTag(STATUS_PERSON);
            view.setBackground(null);
            view.setGravity(Gravity.CENTER);
            view.setTextColor(Color.BLACK);
            view.setText("" + (i + 1));
            layoutPerson.addView(view);
            view.setOnClickListener(this);
            if (i==0) view.setBackgroundResource(R.drawable.selected_seat);
        }
    }
    void createSeatsList(){
        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 0, 8 * seatGaping, 0);
        layoutSeats.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;
        for (int i = 1; i <= NUM_ROW; i++){
            layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layoutSeat.addView(layout);

            for (int j = 1; j <= NUM_COL; j++){
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                if (j==3) layoutParams.weight = 0.25f;
                else layoutParams.weight = 1f;
                view.setLayoutParams(layoutParams);
                view.setGravity(Gravity.CENTER);
                count += (j != 3 ? 1 : 0);
                view.setId(count);
                if (j == 3){
                    view.setTextColor(Color.BLACK);
                    view.setText(i + "");
                    view.setTag(STATUS_NONE);
                } else
                if (check(count)){
                    view.setBackgroundResource(R.drawable.booked_seat_bigger);
                    view.setTag(STATUS_BOOKED);
                } else {
                    view.setBackgroundResource(R.drawable.available_seat);
                    view.setTag(STATUS_AVAILABLE);
                }
                layout.addView(view);
                if (j != 3) {
                    view.setOnClickListener(this);
                }

            }
        }
    }
    private String convertCountToID(int count) {
        int row = (count + 3) / 4;
        int colId = count % 4;
        if (colId == 0) colId = 4;
        char col = (char) (colId + 64);
        return row + "" + col;
    }
    boolean check(int count) {
        return seatsBooked.contains(convertCountToID(count));
    }
    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == STATUS_PERSON){
            View previousView = findViewById(curPassenger + CONSTANT);
            previousView.setBackgroundResource(0);
            curPassenger = view.getId() - CONSTANT;
            view.setBackgroundResource(R.drawable.selected_seat);
        } else
        if ((int) view.getTag() == STATUS_AVAILABLE){
            for (int i = 0; i < seatsSelected.size(); i++){
                if (seatsSelected.get(i) == view.getId() && curPassenger - 1 != i){
                    Toast.makeText(this, "Seat " + convertCountToID(view.getId()) + " is already selected", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            int previousSeat = seatsSelected.get(curPassenger - 1);
            if (previousSeat == view.getId()){
                seatsSelected.set(curPassenger - 1, -1);
                numSeatChoosen--;
                view.setBackgroundResource(R.drawable.available_seat);
                handleText();
                return;
            }
            if (previousSeat != -1){
                View previousView = findViewById(previousSeat);
                previousView.setBackgroundResource(R.drawable.available_seat);
                numSeatChoosen--;
            }
            seatsSelected.set(curPassenger - 1, view.getId());
            numSeatChoosen++;
            view.setBackgroundResource(R.drawable.selected_seat);
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + convertCountToID(view.getId()) + " is Booked", Toast.LENGTH_SHORT).show();
        }
        handleText();
    }
    void handleText(){
        String seatText = "";
        int pos = 0;
        for (int i = 0; i < seatsSelected.size(); i++)
            if (seatsSelected.get(i) != -1) pos = i;
        for (int i = 0; i < seatsSelected.size(); i++){
            if (seatsSelected.get(i) != -1){
                seatText += (i+1) + "-" + convertCountToID(seatsSelected.get(i));
                if (i != pos) seatText += ", ";
            }
        }
        totalSeatText.setText(seatText);
        totalPriceText.setText("$" + numSeatChoosen * realPrice);
    }
}