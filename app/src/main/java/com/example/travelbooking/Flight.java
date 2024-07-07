package com.example.travelbooking;

public class Flight {
    private String flight_id;
    private String flight_from;
    private String flight_3lfrom;
    private String flight_to;
    private String flight_3lto;
    private String flight_date;
    private String flight_time;
    private String flight_price;

    public Flight(String flight_id, String flight_from, String flight_3lfrom, String flight_to, String flight_3lto, String flight_date, String flight_time, String flight_price) {
        this.flight_id = flight_id;
        this.flight_from = flight_from;
        this.flight_3lfrom = flight_3lfrom;
        this.flight_to = flight_to;
        this.flight_3lto = flight_3lto;
        this.flight_date = flight_date;
        this.flight_time = flight_time;
        this.flight_price = flight_price;
    }

    public String getFlightID() {
        return flight_id;
    }

    public String getFlightFrom() {
        return flight_from;
    }

    public String getFlight3lFrom() {
        return flight_3lfrom;
    }

    public String getFlightTo() {
        return flight_to;
    }

    public String getFlight3lTo() {
        return flight_3lto;
    }

    public String getFlightDate() {
        return flight_date;
    }

    public String getFlightTime() {
        return flight_time;
    }

    public String getFlightPrice() {
        return flight_price;
    }

    public void setFlightID(String flight_id) {
        this.flight_id = flight_id;
    }
    public void setFlightFrom(String flight_from) {
        this.flight_from = flight_from;
    }
    public void setFlight3lFrom(String flight_3lfrom) {
        this.flight_3lfrom = flight_3lfrom;
    }
    public void setFlightTo(String flight_to) {
        this.flight_to = flight_to;
    }
    public void setFlight3lTo(String flight_3lto) {
        this.flight_3lto = flight_3lto;
    }
    public void setFlightDate(String flight_date) {
        this.flight_date = flight_date;
    }
    public void setFlightTime(String flight_time) {
        this.flight_time = flight_time;
    }
    public void setFlightPrice(String flight_price) {
        this.flight_price = flight_price;
    }
}
