package com.example.travelbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_startup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        onBoarding1Fragment onBoarding1Fragment = new onBoarding1Fragment();
        onBoarding2Fragment onBoarding2Fragment = new onBoarding2Fragment();
        onBoarding3Fragment onBoarding3Fragment = new onBoarding3Fragment();


        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_Startup, onBoarding1Fragment).commit();

        Button button = findViewById(R.id.startButton_Startup);
        button.setOnClickListener(v -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_Startup, onBoarding2Fragment).addToBackStack(null).commit();
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 1){
                button.setText("Let's start");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_Startup, onBoarding3Fragment).addToBackStack(null).commit();
            } else {
                Intent i = new Intent(StartupActivity.this, WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        //click back system button
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                button.setText("Next");
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                button.setText("Next");
            } else {
                button.setText("Let's start");
            }
        });


    }

}