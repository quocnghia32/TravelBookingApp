package com.example.travelbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.slider.RangeSlider;

import java.text.NumberFormat;

public class FilterActivity extends AppCompatActivity {
    RangeSlider rangeSlider;
    TextView priceFrom, priceTo;
    Button applyFilter, resetFilter;
    ImageButton backButton;
    RadioButton priceButton, AM12, AM06, PM06, PM12;
    int lowPrice = 0, highPrice = 300;
    int lowTime = 0, highTime = 24;
    boolean isSortingOnPrice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rangeSlider = findViewById(R.id.priceSlider);
        priceFrom = findViewById(R.id.priceFrom);
        priceTo = findViewById(R.id.priceTo);
        applyFilter = findViewById(R.id.applyFilter);
        resetFilter = findViewById(R.id.resetFilter);
        priceButton = findViewById(R.id.price_filter);
        AM12 = findViewById(R.id.AM12);
        AM06 = findViewById(R.id.AM06);
        PM06 = findViewById(R.id.PM06);
        PM12 = findViewById(R.id.PM12);
        backButton = findViewById(R.id.back_button_flights);

        priceFrom.setText("$" + rangeSlider.getValues().get(0).intValue());
        priceTo.setText("$" + rangeSlider.getValues().get(1).intValue());

        rangeSlider.addOnChangeListener((slider, value, fromUser) -> {
            // Respond to value change
            priceFrom.setText("$" + rangeSlider.getValues().get(0).intValue());
            priceTo.setText("$" + rangeSlider.getValues().get(1).intValue());

        });
        NumberFormat format = NumberFormat.getCurrencyInstance();
        rangeSlider.setLabelFormatter(value -> format.format(value));

        applyFilter.setOnClickListener(v -> {
            lowPrice = rangeSlider.getValues().get(0).intValue();
            highPrice = rangeSlider.getValues().get(1).intValue();
            if (AM12.isChecked()) {
                lowTime = 0;
                highTime = 6;
            } else if (AM06.isChecked()) {
                lowTime = 6;
                highTime = 12;
            } else if (PM12.isChecked()) {
                lowTime = 12;
                highTime = 18;
            } else if (PM06.isChecked()) {
                lowTime = 18;
                highTime = 24;
            } else {
                lowTime = 0;
                highTime = 24;
            }
            isSortingOnPrice = priceButton.isChecked();
            Intent intent = new Intent();
            intent.putExtra("lowPrice", lowPrice);
            intent.putExtra("highPrice", highPrice);
            intent.putExtra("isSortingOnPrice", isSortingOnPrice);
            intent.putExtra("lowTime", lowTime);
            intent.putExtra("highTime", highTime);
            setResult(RESULT_OK, intent);
            finish();
        });
        resetFilter.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("lowPrice", 0);
            intent.putExtra("highPrice", 300);
            intent.putExtra("isSortingOnPrice", false);
            intent.putExtra("lowTime", 0);
            intent.putExtra("highTime", 24);
            setResult(RESULT_OK, intent);
            finish();
        });
        backButton.setOnClickListener(v -> finish());
    }
}