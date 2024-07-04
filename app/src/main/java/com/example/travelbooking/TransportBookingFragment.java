package com.example.travelbooking;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransportBookingFragment extends Fragment {

    int yearA, monthA, dayA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transport_booking, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onResume() {
        super.onResume();
        //Set Array Adapter for AutoCompleteTextView
        AutoCompleteTextView autoCompleteTextView = getView().findViewById(R.id.FromSelection);
        AutoCompleteTextView autoCompleteTextView2 = getView().findViewById(R.id.ToSelection);
        String[] arrayList = getResources().getStringArray(R.array.destinations);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, arrayList);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, arrayList);

        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView2.setAdapter(arrayAdapter2);

        ImageButton back = getView().findViewById(R.id.back_button);
        ImageButton swap = getView().findViewById(R.id.swap_button);

        //Back Button
        back.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
        });
        //Swap Button
        swap.setOnClickListener(v -> {
            String temp = autoCompleteTextView2.getText().toString();
            int pos = arrayAdapter.getPosition(autoCompleteTextView.getText().toString());
            if (pos == -1) pos = 0;
            autoCompleteTextView2.setText(arrayList[pos].toString(), false);
            pos = arrayAdapter.getPosition(temp);
            if (pos == -1) pos = 1;
            autoCompleteTextView.setText(arrayList[pos].toString(), false);
            resetArray();
        });

        //Date Picker
        TextView fromDate = getView().findViewById(R.id.FromDate);
        TextView toDate = getView().findViewById(R.id.ToDate);
        final Calendar calendar = Calendar.getInstance();

        yearA = calendar.get(Calendar.YEAR);
        monthA = calendar.get(Calendar.MONTH);
        dayA = calendar.get(Calendar.DAY_OF_MONTH);

        //Set the date to the current date
        fromDate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
        toDate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

        fromDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String dateString = year + " " + month + " " + dayOfMonth;

                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(year, month, dayOfMonth);
                    calendar.set(year, month, dayOfMonth);

                    fromDate.setText(SimpleDateFormat.getDateInstance().format(calendar1.getTime()));
                }
            }, yearA, monthA, dayA);
            datePickerDialog.show();
        });

        toDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String dateString = year + " " + month + " " + dayOfMonth;
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(year, month, dayOfMonth);
                    //Check if toDate is before fromDate
                    if (calendar1.before(calendar)) {
                        Toast.makeText(getContext(), "Please select a date after the from date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    toDate.setText(SimpleDateFormat.getDateInstance().format(calendar1.getTime()));

                }
            }, yearA, monthA, dayA);
            datePickerDialog.show();
        });

        //Search Button
        Button search = getView().findViewById(R.id.search_booking);
        search.setOnClickListener(v -> {
            if (autoCompleteTextView.getText().toString().isEmpty() || autoCompleteTextView2.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getContext(), TransportFlightsActivity.class);
            intent.putExtra("from", autoCompleteTextView.getText().toString());
            intent.putExtra("to", autoCompleteTextView2.getText().toString());
            intent.putExtra("fromDate", fromDate.getText().toString());
            intent.putExtra("toDate", toDate.getText().toString());
            startActivity(intent);
        });

    }

    private void resetArray() {
        AutoCompleteTextView autoCompleteTextView = getView().findViewById(R.id.FromSelection);
        AutoCompleteTextView autoCompleteTextView2 = getView().findViewById(R.id.ToSelection);
        String[] arrayList = getResources().getStringArray(R.array.destinations);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, arrayList);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, arrayList);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView2.setAdapter(arrayAdapter2);
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}