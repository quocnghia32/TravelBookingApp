package com.example.travelbooking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class NotAvailableFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        ImageButton back = getView().findViewById(R.id.back_button_notAvailable);
        //Back Button
        back.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
        });
    }
}