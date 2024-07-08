package com.example.travelbooking;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    EditText username, password, firstName, lastName, email, phone;
    Button signupButton;
    ImageButton backButton;
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
        backButton = findViewById(R.id.back_button_signup);

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
        backButton.setOnClickListener(v -> finish());
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}