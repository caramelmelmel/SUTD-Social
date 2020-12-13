package com.example.sutd_social.firebase;

public class Bulletin {
    private static final String TAG = "Bulletin";
    public String title;
    public String description;
    public String fifthRow;
    public String image;
    public String url;
    public String expiryDate;

    Bulletin() {
    }
    
    Bulletin(String title, String description, String fifthRow, String image, String url, String expiryDate) {
        this.fifthRow = fifthRow;
        this.title = title;
        this.description = description;
        this.image = image;
        this.url = url;
        this.expiryDate = expiryDate;
    }

    public Bulletin(String title, String description, String fifthRow, String image, String expiryDate) {
        this.title = title;
        this.description = description;
        this.fifthRow = fifthRow;
        this.image = image;
        this.expiryDate = expiryDate;
    }
}
