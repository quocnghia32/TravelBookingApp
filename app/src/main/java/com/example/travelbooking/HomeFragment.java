package com.example.travelbooking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        ImageButton searchButton = view.findViewById(R.id.search_button);
        EditText editText = view.findViewById(R.id.search_text);
        ImageButton transportButton = view.findViewById(R.id.transport_button);
        ImageButton hotelButton = view.findViewById(R.id.hotel_button);
        ImageButton eventButton = view.findViewById(R.id.event_button);
        ImageButton tripButton = view.findViewById(R.id.trips_button);

        // Set an OnClickListener on the button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
        transportButton.setOnClickListener(v -> {
            BottomNavigationView bottomNavigationView;
            bottomNavigationView = (BottomNavigationView) ((MainActivity) getActivity()).findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.BookingB);
            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new TransportBookingFragment()).addToBackStack(null).commit();
        });
        hotelButton.setOnClickListener(v -> {
            setNoneFragment();
        });
        eventButton.setOnClickListener(v -> {
            setNoneFragment();
        });
        tripButton.setOnClickListener(v -> {
            setNoneFragment();
        });
        return view;
    }
    private void setNoneFragment() {
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) ((MainActivity) getActivity()).findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.BookingB);
        ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NotAvailableFragment()).addToBackStack(null).commit();
    }
}