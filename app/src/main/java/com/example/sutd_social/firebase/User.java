package com.example.sutd_social.firebase;

import java.util.HashMap;

public class User {
    public String name;
    public String info;
    public String pillar;
    public String fifthRow;
    public String telegram;
    public HashMap<String, Long> skills;

    User(String name, String info, String pillar, String fifthRow, String telegram, HashMap<String, Long> skills) {
        this.name = name;
        this.info = info;
        this.pillar = pillar;
        this.fifthRow = fifthRow;
        this.telegram = telegram;
        this.skills = skills;
    }
}
