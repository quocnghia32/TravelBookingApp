package com.example.travelbooking;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeeklyView extends LinearLayout {

    private List<Event> events;
    private OnDaySelectedListener onDaySelectedListener;
    private String selectedDate = "";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
    private Typeface poppinsMedium;
    private Typeface poppinsSemiBold;
    private int dayWidth;
    private int month;
    private int year;
    private int daysInMonth;
    HorizontalScrollView scrollView;

    public WeeklyView(Context context) {
        super(context);

        init(context);
    }

    public WeeklyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WeeklyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        poppinsMedium = ResourcesCompat.getFont(context, R.font.poppins_medium);
        poppinsSemiBold = ResourcesCompat.getFont(context, R.font.poppins_semibold);
        calculateDayWidth(context);
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        populateDays();
    }

    public void setMonthYear(int year, int month) {
        this.year = year;
        this.month = month;
        populateDays();
    }

    public void setOnDaySelectedListener(OnDaySelectedListener listener) {
        this.onDaySelectedListener = listener;
    }

    private void calculateDayWidth(Context context) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        dayWidth = screenWidth / 7;
    }

    private void populateDays() {
        removeAllViews();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < daysInMonth; i++) {
            final String currentDate = dateFormat.format(calendar.getTime());

            LinearLayout dayLayout = new LinearLayout(getContext());
            dayLayout.setOrientation(VERTICAL);
            dayLayout.setGravity(Gravity.CENTER);
            dayLayout.setLayoutParams(new LayoutParams(dayWidth, LayoutParams.WRAP_CONTENT));
            dayLayout.setPadding(16, 16, 16, 16);
            updateBackground(dayLayout, currentDate);

            TextView dayName = new TextView(getContext());
            dayName.setText(getAbbreviatedDayName(calendar));
            dayName.setTextColor(Color.BLACK);
            dayName.setGravity(Gravity.CENTER);
            dayName.setTypeface(poppinsMedium);

            TextView dayDate = new TextView(getContext());
            dayDate.setText(currentDate);
            dayDate.setTextColor(Color.BLACK);
            dayDate.setGravity(Gravity.CENTER);
            dayDate.setTypeface(poppinsSemiBold);

            dayLayout.addView(dayName);
            dayLayout.addView(dayDate);

            dayLayout.setOnClickListener(v -> {
                selectedDate = currentDate;
                updateAllDays();
                if (onDaySelectedListener != null) {
                    onDaySelectedListener.onDaySelected(currentDate);
                }
            });

            addView(dayLayout);

            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private String getAbbreviatedDayName(Calendar calendar) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        String dayName = dayFormat.format(calendar.getTime());
        return dayName.substring(0, 2).toUpperCase(Locale.getDefault());
    }

    private void updateBackground(LinearLayout dayLayout, String currentDate) {
        if (currentDate.equals(selectedDate)) {
            dayLayout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.day_background));
        } else {
            dayLayout.setBackground(null);
        }
    }

    private void updateAllDays() {
        for (int i = 0; i < getChildCount(); i++) {
            View dayView = getChildAt(i);
            if (dayView instanceof LinearLayout) {
                LinearLayout dayLayout = (LinearLayout) dayView;
                TextView dayDate = (TextView) dayLayout.getChildAt(1); // Assuming the date TextView is the second child
                String currentDate = dayDate.getText().toString();
                updateBackground(dayLayout, currentDate);
            }
        }
    }

    public interface OnDaySelectedListener {
        void onDaySelected(String date);
    }
    public int setSelectedDate(String date) {
        selectedDate = date;
        updateAllDays();
        int offset = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View dayView = getChildAt(i);
            if (dayView instanceof LinearLayout) {
                LinearLayout dayLayout = (LinearLayout) dayView;
                TextView dayDate = (TextView) dayLayout.getChildAt(1); // Assuming the date TextView is the second child
                String currentDate = dayDate.getText().toString();
                if (currentDate.equals(date)) {
                    offset -= 3*dayWidth;
                    offset = Math.max(offset, 0);
                    offset = Math.min(offset, (daysInMonth - 7) * dayWidth);
                    return offset;
                } else offset += dayWidth;
            }
        }
        return -1;
    }
}
