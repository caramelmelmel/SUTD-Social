package com.example.sutd_social.firebase;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bulletin {
    private static final String TAG = "Bulletin";
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // use this with format() or parse()
    public String title;
    public String description;
    public String fifthRow;
    public String image;
    public String url;
    public String expiryDate;

    Bulletin(){}

    Bulletin(String title, String description, String fifthRow, String image, String url, String expiryDate) {
        this.fifthRow = fifthRow;
        this.title = title;
        this.description = description;
        this.image = image;
        this.url = url;
        this.expiryDate = expiryDate;

        // Decide if expiryDate should be string or date
        // do up class and try store to firebase, see how it stores
        // retrieve the Class should be the only other function
        // together with storing all in an HashMap of <id>, <bulletin>
    }
// Added new constructor for 2 way population
    public Bulletin(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static String dateFormat(Date date) {
        return dateFormat.format(date);
    }

    public static Date dateParse(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            Log.e(TAG, "dateParse: An error has occurred while parsing the date" );
            e.printStackTrace();
            return null;
        }
    }
}
