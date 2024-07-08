package com.example.travelbooking;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    EditText username, password, firstName, lastName, email, phone;
    Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        username = findViewById(R.id.username_signup);
        password = findViewById(R.id.password_signup);
        firstName = findViewById(R.id.firstname_singup);
        lastName = findViewById(R.id.lastname_signup);
        email = findViewById(R.id.email_signup);
        phone = findViewById(R.id.phone_signup);
        signupButton = findViewById(R.id.signup_signup);

        signupButton.setOnClickListener(v -> {
            MyDatabaseHelper DB = new MyDatabaseHelper(SignupActivity.this);
            Cursor cursor = DB.readByUsername(username.getText().toString());
            if (cursor.getCount() > 0) {
                Toast.makeText(SignupActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.getText().toString().isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please set password", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User(firstName.getText().toString(), lastName.getText().toString(), phone.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString(), null);
            boolean result = DB.addUser(user);
            if (result) Toast.makeText(SignupActivity.this, "Sign Up successfully", Toast.LENGTH_SHORT).show();
            else Toast.makeText(SignupActivity.this, "Sign Up failed", Toast.LENGTH_SHORT).show();
            finish();
        });



    }
}