package com.example.travelbooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class BookingFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();

        ImageButton transport = getView().findViewById(R.id.booking_transport);
        ImageButton hotel = getView().findViewById(R.id.booking_hotel);
        ImageButton event = getView().findViewById(R.id.booking_event);
        ImageButton trip = getView().findViewById(R.id.booking_trips);

        // Set onClickListeners for each ImageButton
        transport.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_Main, new TransportBookingFragment()).addToBackStack(null).commit();
        });
        hotel.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_Main, new NotAvailableFragment()).addToBackStack(null).commit();
        });
        event.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_Main, new NotAvailableFragment()).addToBackStack(null).commit();
        });
        trip.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_Main, new NotAvailableFragment()).addToBackStack(null).commit();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }
}