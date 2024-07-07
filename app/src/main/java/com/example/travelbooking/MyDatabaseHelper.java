package com.example.travelbooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "User.db";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_NAME = "User";
    private static final String COLUMN_FIRSTNAME = "first_name";
    private static final String COLUMN_LASTNAME = "last_name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_IMAGE = "image";

    private static final String FLIGHT_TABLE_NAME = "Flight";
    private static final String COLUMN_FLIGHT_ID = "flight_id";
    private static final String COLUMN_FLIGHT_FROM = "flight_from";
    private static final String COLUMN_FLIGHT_3LFROM = "flight_3lfrom";
    private static final String COLUMN_FLIGHT_TO = "flight_to";
    private static final String COLUMN_FLIGHT_3LTO = "flight_3lto";
    private static final String COLUMN_FLIGHT_DATE = "flight_date";
    private static final String COLUMN_FLIGHT_TIME = "flight_time";
    private static final String COLUMN_FLIGHT_PRICE = "flight_price";

    private static final String SEAT_TABLE_NAME = "Seat";
    private static final String COLUMN_SEAT_USERID = "username";
    private static final String COLUMN_SEAT_FLIGHTID = "flight_id";
    private static final String COLUMN_SEAT_SEATNUMBER = "seat_number";



    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" +
                COLUMN_FIRSTNAME + " TEXT, " +
                COLUMN_LASTNAME + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_IMAGE + " BLOB);";
        String query2 = "CREATE TABLE " + FLIGHT_TABLE_NAME +
                " (" +
                COLUMN_FLIGHT_ID + " TEXT PRIMARY KEY, " +
                COLUMN_FLIGHT_FROM + " TEXT, " +
                COLUMN_FLIGHT_3LFROM + " TEXT, " +
                COLUMN_FLIGHT_TO + " TEXT, " +
                COLUMN_FLIGHT_3LTO + " TEXT, " +
                COLUMN_FLIGHT_DATE + " TEXT, " +
                COLUMN_FLIGHT_TIME + " TEXT, " +
                COLUMN_FLIGHT_PRICE + " TEXT);";
        String query3 = "CREATE TABLE " + SEAT_TABLE_NAME +
                " (" +
                COLUMN_SEAT_USERID + " TEXT, " +
                COLUMN_SEAT_FLIGHTID + " TEXT, " +
                COLUMN_SEAT_SEATNUMBER + " TEXT, " +
                "PRIMARY KEY (" + COLUMN_SEAT_FLIGHTID + ", " + COLUMN_SEAT_SEATNUMBER + "), " +
                "FOREIGN KEY (" + COLUMN_SEAT_USERID + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_USERNAME + "), " +
                "FOREIGN KEY (" + COLUMN_SEAT_FLIGHTID + ") REFERENCES " + FLIGHT_TABLE_NAME + "(" + COLUMN_FLIGHT_ID + "));";
        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FLIGHT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SEAT_TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(User currentUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRSTNAME, currentUser.getFirstName());
        cv.put(COLUMN_LASTNAME, currentUser.getLastName());
        cv.put(COLUMN_PHONE, currentUser.getPhone());
        cv.put(COLUMN_EMAIL, currentUser.getEmail());
        cv.put(COLUMN_USERNAME, currentUser.getUsername());
        cv.put(COLUMN_PASSWORD, currentUser.getPassword());
        cv.put(COLUMN_IMAGE, currentUser.getImage());
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    public boolean addFlight(Flight currentFlight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FLIGHT_ID, currentFlight.getFlightID());
        cv.put(COLUMN_FLIGHT_FROM, currentFlight.getFlightFrom());
        cv.put(COLUMN_FLIGHT_3LFROM, currentFlight.getFlight3lFrom());
        cv.put(COLUMN_FLIGHT_TO, currentFlight.getFlightTo());
        cv.put(COLUMN_FLIGHT_3LTO, currentFlight.getFlight3lTo());
        cv.put(COLUMN_FLIGHT_DATE, currentFlight.getFlightDate());
        cv.put(COLUMN_FLIGHT_TIME, currentFlight.getFlightTime());
        cv.put(COLUMN_FLIGHT_PRICE, currentFlight.getFlightPrice());
        long result = db.insert(FLIGHT_TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    public boolean addSeat(String username, String flight_id, String seat_number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SEAT_USERID, username);
        cv.put(COLUMN_SEAT_FLIGHTID, flight_id);
        cv.put(COLUMN_SEAT_SEATNUMBER, seat_number);
        long result = db.insert(SEAT_TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
    Cursor readAllFlights(){
        String query = "SELECT * FROM " + FLIGHT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor readFlights(int year, int month, String day, String from, String to){
        String FlightDate = year + "-";
        if (month < 10)
            FlightDate += "0";
        FlightDate += month + "-" + day;
        String query = "SELECT * FROM " + FLIGHT_TABLE_NAME +
                " WHERE " + COLUMN_FLIGHT_DATE + " = '" + FlightDate +
                "' AND " + COLUMN_FLIGHT_3LFROM + " = '" + from +
                "' AND " + COLUMN_FLIGHT_3LTO + " = '" + to + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Log.i("readFlightsByDate", query);
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor readByUsernamePassword(String username, String password){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = '" + username + "' AND " + COLUMN_PASSWORD + " = '" + password + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor readByUsername(String username){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = '" + username + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String firstName, String lastName, String phone, String email, String username, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRSTNAME, firstName);
        cv.put(COLUMN_LASTNAME, lastName);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_IMAGE, image);
        long result = db.update(TABLE_NAME, cv, COLUMN_USERNAME+"=?", new String[]{username});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_USERNAME+"=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + FLIGHT_TABLE_NAME);
    }

    public Bitmap getImage(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = " + username;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            //7 is column index of image
            byte[] bitmap = cursor.getBlob(7);
            cursor.close();
            return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
        }
        cursor.close();
        return null;
    }


}