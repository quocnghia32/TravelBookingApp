package com.example.travelbooking;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    public String username;
    public String password;
    MyDatabaseHelper db = new MyDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.back_button);
        Button loginButton = findViewById(R.id.login_button);
        EditText usernameEdit = findViewById(R.id.username);
        EditText passwordEdit = findViewById(R.id.password);

        backButton.setOnClickListener(v -> {
            finish();
        });
        loginButton.setOnClickListener(v -> {
            username = usernameEdit.getText().toString();
            password = passwordEdit.getText().toString();
            if (check(username,password)) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("username", username);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean check(String username, String password) {
        Cursor cursor = db.readByUsernamePassword(username, password);
        return cursor.getCount() > 0;
    }
}