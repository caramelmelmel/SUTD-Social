package com.example.sutd_social.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
write read from database return skills
get and set function (enclose into one method)
setter will set the method
 */

public class MatchingAlgo {
    private static final boolean lock = false;
    //Tag for testing on the logcat standardised
    private static final String TAG = "MatchingAlgo";
    //attributes for the algo,self explanatory
    public static HashMap<String, String> student_name;
    public static HashMap<String, String> student_info;
    public static HashMap<String, String> student_pillar;

    //NOTE skillset confidence from the skills to the percentage confidence for eac
    public static HashMap<String, HashMap<String, Long>> skillset = new HashMap();

    //restore data into the following hashmap
    public static HashMap<String, long[]> sk_conf = new HashMap();

    //define an array of long to store the data
    //we hard code a long because long is boss
    public long[] confidence = new long[1000];
    //generate the match
    public double match;
    //get the average to use as the base point comparison
    public long meanUserconf;
    public long avgNumSkill;
    //generate user arraylist for the user object
    ArrayList<User> users = new ArrayList<User>();
    //create the following to store the attributes inside after adding
    private FirebaseUser mFirebaseAuth;
    private DatabaseReference dbref;
    private long basepoint;

    //constructor to store hashmap in the following style
    // -> represents the mapping
    // skill -> id -> level
    //note that this is ONLY for the individual skill
    public MatchingAlgo() {
        skillset = getSkillset();
    }


    //get skill that each user has to their confidence level
    //hashmap of skill as the main connection point
    /* Structure on firebase:
    skill
         |---- id
                |--- level
     */
    //skill->id->level
    public static HashMap<String, HashMap<String, Long>> getSkillset() {
        HashMap<String, HashMap<String, Long>> skillIdsLevel = new HashMap<>();
        HashMap<String, HashMap<String, Long>> idSkillsLevel = Social.getSkills();

        for (Map.Entry<String, HashMap<String, Long>> idSkills : idSkillsLevel.entrySet()) {
            String id = idSkills.getKey();
            for (Map.Entry<String, Long> skillLevel : idSkills.getValue().entrySet()) {
                String skill = skillLevel.getKey();
                Long level = skillLevel.getValue();
                if (skillIdsLevel.containsKey(skill)) {
                    // Skill is inside
                    skillIdsLevel.get(skill).put(id, level);
                } else {
                    // Skill not inside yet
                    HashMap<String, Long> idLevel = new HashMap<>();
                    idLevel.put(id, level);
                    skillIdsLevel.put(skill, idLevel);
                }
            }
        }
        return skillIdsLevel;
    }

    //set mean user confidence to 0 as start
    public void setMean_user_conf() {
        meanUserconf = 0;
    }

    //getter method for the skillset, match the skill with the array values of confidence that each user
    //has marked out for that specific skillset
    //long array here is the collection of the scores of every user for average calculation later on

    public HashMap<String, long[]> retrieve() {
        for (Map.Entry<String, HashMap<String, Long>> skillIdEntry : skillset.entrySet()) {
            long[] levelArr = new long[skillIdEntry.getValue().size()];
            Iterator<Long> levelIter = skillIdEntry.getValue().values().iterator();
            for (int i = 0; levelIter.hasNext(); i++) {
                levelArr[i] = levelIter.next();
            }
            sk_conf.put(skillIdEntry.getKey(), levelArr);
        }

        return sk_conf;
    }

    //get the confidence array, the collection of confidence scores from the users and put it as an array list
    public long[] getConf_arr(HashMap<String, long[]> sk_conf, String sk) {
        confidence = sk_conf.get(sk);
        return confidence;
    }

    //average score of confidence of all the users in the database
    public long stats_bp(long[] confidence) {
        for (int a = 0; a < confidence.length; a++) {
            meanUserconf += confidence[a];
        }
        meanUserconf = meanUserconf / confidence.length;
        return meanUserconf;
    }

    //consider the other metric of number of skillsets of each user if not everyone will have 100% match which is a pure waste of time writing this if that is what we are aiming for
    //for precision sake, we store in long
    public long getNumSkillsets(String id) {
        //get the number of skillsets from each user
        HashMap<String, Long> UserSkills = Social.getSkills(id);
        return UserSkills.size();
    }

    //generate base point on num of skillsets
    //average number of skillsets among the user population
    public long avgNumSkillsets() {
        //numId is the total number of users
        //query social.java
        int numId = Social.getName().size();
        int numOfSkills = 0;
        for (HashMap<String, Long> skill : skillset.values()) {
            numOfSkills += skill.size();
        }
        return numOfSkills / numId;
    }

    //get the confidence score of the user for the specific skill he has
    //the input data is skill-> id -> confidence of that particular user
    public long getUserskillLevel(HashMap<String, HashMap<String, Long>> skillset, String skill, String id) {
        Log.i(TAG, "User skill is below");
        return skillset.get(skill).get(id);
    }

    //do the euclidean distance if the confidence is lower than the mean
    public double generateEuclideanDistance(HashMap<String, HashMap<String, Long>> skillset, String skill, String id) {
        long x_dist = getUserskillLevel(skillset, skill, id) - meanUserconf;
        long y_dist = getNumSkillsets(id) - avgNumSkillsets();
        match = Math.hypot((x_dist), (y_dist));
        Log.i(TAG, "done generating the euc distance");
        return match;
    }

    //create hashmap user id to distance for that skill query
    // id ->distance use this before comparator
    //  (user_distance-min)/max-min *100

    public HashMap<String, Double> percentageGenerated(HashMap<String, HashMap<String, Long>> skillset, String skill) {
        HashMap<String, Double> percent = new HashMap();
        for (String id : sk_conf.keySet()) {
            double distCompare = generateEuclideanDistance(skillset, skill, id);
            percent.put(id, distCompare);
        }

        Double min = Collections.min(percent.values());
        Double max = Collections.max(percent.values());

        for (Map.Entry<String, Double> entry : percent.entrySet()) {
            Double p = ((entry.getValue() - min) / (max - min)) * 100;
            percent.put(entry.getKey(), p);
        }
        Log.i(TAG, "percent");
        return percent;

    }

    //final return
    public ArrayList<Map.Entry<String, Double>> sortId(HashMap<String, Double> percent) {
        ArrayList<Map.Entry<String, Double>> list = new ArrayList<>(percent.entrySet());
        Log.i(TAG, "ArrayList is done");
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return list;
    }

    //retrieve from above
    //return the array list in the format of the id -> calculated score of the user where the sorted array is in order
    //of the skills score to populate the cards
    //takes into the score of that user with respect to the skill
    public ArrayList<Map.Entry<String, Double>> getTheStats(String id, String skill) {
        String putSkilltoLowerCase;
        sk_conf = retrieve();
        confidence = getConf_arr(sk_conf, skill);
        //get percent here
        HashMap<String, Double> percent = percentageGenerated(skillset, skill);
        //finally return the arraylist
        ArrayList<Map.Entry<String, Double>> list = sortId(percent);
        return list;

    }

    public ArrayList<Map.Entry<String, Double>> skillsIsAllSmallCaps(String id, String skill){
        String new_stuff = skill.toLowerCase();
        ArrayList<Map.Entry<String, Double>> ls= getTheStats(id,new_stuff);
        return ls;
    }


}