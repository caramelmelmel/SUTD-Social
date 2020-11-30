package com.example.sutd_social;
import com.example.sutd_social.firebase.Social;
import com.example.sutd_social.firebase.User;
//import com.example.sutd_social.User;
import com.example.sutd_social.firebase.MatchingAlgo;

import java.util.ArrayList;
import java.util.HashMap;//this class implements the methods from the Social.java
import java.util.Map;

public class Population extends Social{
    public HashMap<String, String> nameAndid;
    public String skills;
    public User userDeets;
    public String cardIndivname;
    public String id;
    public String pillar;
    public ArrayList<Map.Entry<String, Double>> sendHelpInstance;
    //instantiate the class for the matching algo
    MatchingAlgo a = new MatchingAlgo();

    //use this to implement the firebase UI
    public Population() {
    }

    //use getter and setter to push to firebase for the purpose of the card population
    //returns id -> confidence algo
    public ArrayList<Map.Entry<String, Double>> getSortedMatchAlgo(String id, String skill) {
        //generate the array list
        sendHelpInstance = a.getTheStats(id, skill);
        return sendHelpInstance;
    }

    //returns the new confidence for the user id to be pushed to firebase
    public Double updatedUserclass(ArrayList<Map.Entry<String, Double>> sendHelpInstance, String skill) {
        //access the list through a for loop
        double confinSkill = 0;
        //retrieve stuff for user
        sendHelpInstance = getSortedMatchAlgo(id, skill);
        for (int i = 0; i < sendHelpInstance.size(); i++) {
            if(sendHelpInstance.get(i).getKey()== id){
                confinSkill = sendHelpInstance.get(i).getValue();
                break;
        }

    }
        return confinSkill;
    }

    //converts the above to string for storage into firebase
    @Override
    public static User getUser(String id,s){
        String name = getName(id);
        String info = getInfo(id);
        String pillar = getPillar(id);
        String fifthRow = getFifthRow(id);
        String telegram = getTelegram(id);
        String displayPic = getDisplayPic(id);
        HashMap<String, Long> skills = getSkills(id);
        Double confinSkill = updatedUserclass(ArrayList<Map.Entry<String,Double>> sendHelpInstance, String skills.get(id));

        User user = new User(name, info, pillar, fifthRow, telegram, displayPic, skills);
        return user;
    }


   /* public HashMap<String,String> getName(){
        return nameAndid;
    }*/
    //pass everything to user method for the retrieving of the hashmap sorted

    //retrieve sorted array from MatchingAlgo method and write to firebase
    //array list of hashmaps where id is tagged to the calculated confidence level

    //get the user details from the id tag to the name
    //use user class to retrieve the name from id in the sorted array list
    //use the matching algo class to retrieve the user id tagged to the confidence results

    //print the user skills
    //print user pillar
    //output the profile pic


}
