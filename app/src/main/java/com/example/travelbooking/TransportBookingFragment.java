package com.example.travelbooking;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransportBookingFragment extends Fragment {

    int yearFrom, monthFrom, dayFrom;
    int yearTo, monthTo, dayTo;
    RadioButton radioFlight, ecoClass, busClass;
    RadioGroup classGroup;
    String[] destinations;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2;
    EditText numAdults;
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
        autoCompleteTextView = getView().findViewById(R.id.FromSelection_TransBooking);
        autoCompleteTextView2 = getView().findViewById(R.id.ToSelection_TransBooking);
        destinations = getResources().getStringArray(R.array.destinations);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, destinations);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, destinations);

        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView2.setAdapter(arrayAdapter2);

        ImageButton back = getView().findViewById(R.id.back_button_TransBooking);
        ImageButton swap = getView().findViewById(R.id.swap_button_TransBooking);
        radioFlight = getView().findViewById(R.id.radioFlight_TransBooking);
        ecoClass = getView().findViewById(R.id.economy_TransBooking);
        busClass = getView().findViewById(R.id.business_TransBooking);
        classGroup = getView().findViewById(R.id.classGroup_TransBooking);
        numAdults = getView().findViewById(R.id.adult_TransBooking);

        //Back Button
        back.setOnClickListener(v -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().popBackStack();
        });
        //Swap Button
        swap.setOnClickListener(v -> {
            int pos1 = getFromResouce(autoCompleteTextView.getText().toString());
            int pos2 = getFromResouce(autoCompleteTextView2.getText().toString());
            if (pos1 == -1) pos1 = 0;
            if (pos2 == -1) pos2 = 1;
            autoCompleteTextView2.setText(destinations[pos1].toString(), false);
            autoCompleteTextView.setText(destinations[pos2].toString(), false);
            resetArray();
        });

        //Date Picker
        TextView fromDate = getView().findViewById(R.id.FromDate_TransBooking);
        TextView toDate = getView().findViewById(R.id.ToDate_TransBooking);
        Calendar calendarFrom = Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();

        yearFrom = yearTo = calendarFrom.get(Calendar.YEAR);
        monthFrom = monthTo = calendarFrom.get(Calendar.MONTH);
        dayFrom = dayTo = calendarFrom.get(Calendar.DAY_OF_MONTH);

        //Set the date to the current date
        fromDate.setText(SimpleDateFormat.getDateInstance().format(calendarFrom.getTime()));
        toDate.setText(SimpleDateFormat.getDateInstance().format(calendarTo.getTime()));

        fromDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendarFrom.set(year, month, dayOfMonth);
                    fromDate.setText(SimpleDateFormat.getDateInstance().format(calendarFrom.getTime()));
                    yearFrom = year;
                    monthFrom = month;
                    dayFrom = dayOfMonth;
                    if (calendarTo.before(calendarFrom)) {
                        yearTo = year;
                        monthTo = month;
                        dayTo = dayOfMonth;
                        calendarTo.set(year, month, dayOfMonth);
                        toDate.setText(SimpleDateFormat.getDateInstance().format(calendarTo.getTime()));
                    }
                }
            }, yearFrom, monthFrom, dayFrom);
            datePickerDialog.show();
        });

        toDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar currentCalendar = Calendar.getInstance();
                    currentCalendar.set(year, month, dayOfMonth);
                    //Check if toDate is before fromDate
                    if (currentCalendar.before(calendarFrom)) {
                        Toast.makeText(getContext(), "Please select a date after the from date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    calendarTo.set(year, month, dayOfMonth);
                    toDate.setText(SimpleDateFormat.getDateInstance().format(calendarTo.getTime()));
                    yearTo = year;
                    monthTo = month;
                    dayTo = dayOfMonth;
                }
            }, yearTo, monthTo, dayTo);
            datePickerDialog.show();
        });

        //Search Button
        Button search = getView().findViewById(R.id.search_TransBooking);
        search.setOnClickListener(v -> {
            //search if the from and to location is valid
            int pos1 = getFromResouce(autoCompleteTextView.getText().toString());
            int pos2 = getFromResouce(autoCompleteTextView2.getText().toString());
            if (pos1 == -1) {
                Toast.makeText(getContext(), "Please select a valid from location", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pos2 == -1) {
                Toast.makeText(getContext(), "Please select a valid to location", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pos1 == pos2) {
                Toast.makeText(getContext(), "From and To locations cannot be the same", Toast.LENGTH_SHORT).show();
                return;
            }
            //check if radioFlight is not chosen
            if (!radioFlight.isChecked()) {
                Toast.makeText(getContext(), "We currently only support for flight", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getContext(), TransportFlightsActivity.class);
            intent.putExtra("from", autoCompleteTextView.getText().toString());
            intent.putExtra("to", autoCompleteTextView2.getText().toString());
            intent.putExtra("fromDateYear", yearFrom);
            intent.putExtra("fromDateMonth", monthFrom);
            intent.putExtra("fromDateDay", dayTo);
            intent.putExtra("numAdults", Integer.valueOf(numAdults.getText().toString()));
            //put string for class
            int selectedId = classGroup.getCheckedRadioButtonId();
            RadioButton selectedClass = getView().findViewById(selectedId);
            intent.putExtra("class", selectedClass.getText().toString());
            startActivity(intent);
        });

    }

    private void resetArray() {
        autoCompleteTextView= getView().findViewById(R.id.FromSelection_TransBooking);
        autoCompleteTextView2 = getView().findViewById(R.id.ToSelection_TransBooking);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, destinations);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, destinations);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView2.setAdapter(arrayAdapter2);
    }
    private int getFromResouce(String currentString){
        for (int i = 0; i < destinations.length; i++) {
            if (destinations[i].equals(currentString)) {
                return i;
            }
        }
        return -1;
    }
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}