package com.example.sutd_social.firebase;

public class Admin {
    private static Admin ourInstance = null;
    private static String id;

    private Admin() {
    }

    public static Admin init(String id) {
        if (ourInstance == null) {
            ourInstance = new Admin();
            Admin.id = id;
        }
        return ourInstance;
    }

    public static String getUserid() {
        return Admin.id;
    }
}
