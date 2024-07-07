package com.example.travelbooking;

public class Event {
    private String date;
    private String title;

    public Event(String date, String title) {
        this.date = date;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }
}
