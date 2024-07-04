package com.example.travelbooking;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelbooking.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    User user;
    String username;
    MyDatabaseHelper db = new MyDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the user from the intent
        if (getIntent().hasExtra("username")) {
            username = getIntent().getStringExtra("username");
            Toast.makeText(this, "Welcome " + username, Toast.LENGTH_SHORT).show();
            getUser();
            Cursor cursor = db.readByUsername(username);
        }
        // Set the home fragment as the default fragment
        replaceFragment(new HomeFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            for (int i = fragmentManager.getBackStackEntryCount(); i > 0; i--) {
                fragmentManager.popBackStackImmediate();
            }
            if (item.getItemId() == R.id.HomeB) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.BookingB) {
                replaceFragment(new BookingFragment());
            } else if (item.getItemId() == R.id.NotificationB) {
                replaceFragment(new NotificationFragment());
            } else if (item.getItemId() == R.id.AccountB) {
                replaceFragment(new AccountFragment());
            }
            return true;
        });


    }
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    // Hide the keyboard and defocus when the user taps outside of an EditText
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
    private void getUser() {
        Cursor cursor = db.readByUsername(username);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String firstName = cursor.getString(1);
            String lastName = cursor.getString(2);
            String phone = cursor.getString(3);
            String email = cursor.getString(4);
            String username = cursor.getString(5);
            String password = cursor.getString(6);
            byte[] image = cursor.getBlob(7);
            user = new User(firstName, lastName, phone, email, username, password, image);
        } else {
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
        }
    }
}