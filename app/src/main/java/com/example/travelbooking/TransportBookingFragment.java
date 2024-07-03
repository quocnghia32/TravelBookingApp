package com.example.travelbooking;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransportBookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransportBookingFragment extends Fragment {

    int year, month, day;

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
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        fromDate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
        toDate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
        fromDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String dateString = year + " " + month + " " + dayOfMonth;

                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(year, month, dayOfMonth);
                    fromDate.setText(SimpleDateFormat.getDateInstance().format(calendar1.getTime()));
                }
            }, year, month, day);
            datePickerDialog.show();
        });
        toDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String dateString = year + " " + month + " " + dayOfMonth;

                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(year, month, dayOfMonth);
                    toDate.setText(SimpleDateFormat.getDateInstance().format(calendar1.getTime()));
                }
            }, year, month, day);
            datePickerDialog.show();
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