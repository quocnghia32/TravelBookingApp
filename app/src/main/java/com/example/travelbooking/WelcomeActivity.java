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
//        registerButton.setOnClickListener(v -> {
//            MyDatabaseHelper db = new MyDatabaseHelper(this);
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            byte[] img = stream.toByteArray();
//            User user = new User("John","Doe","1234567890","johnDoe@gmail.com","2","2",img);
//            boolean success = db.addUser(user);
//            if (success){
//                Toast.makeText(this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Failed to save!", Toast.LENGTH_SHORT).show();
//            }
//
//        });

    }

}