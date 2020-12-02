package com.example.sutd_social;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutd_social.firebase.Admin;
import com.example.sutd_social.firebase.MatchingAlgo;
import com.example.sutd_social.firebase.Social;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //--initialize for find help page
    private RecyclerView find_help_RecView;
    private EditText searchBar;
    private ArrayList<Find_Help> find_help_posts;
    private Button btn_search;
    private FindHelpRecViewAdapter adapter;

    public MatchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchFragment newInstance(String param1, String param2) {
        MatchFragment fragment = new MatchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //--

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        find_help_RecView = view.findViewById(R.id.find_help_RecView);
        searchBar = view.findViewById(R.id.find_help_searchbar);
        btn_search = view.findViewById(R.id.btn_search);

        find_help_posts = new ArrayList<>();


        adapter = new FindHelpRecViewAdapter(getContext());
        adapter.setPosts(find_help_posts);
        find_help_RecView.setAdapter(adapter);
        find_help_RecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity", "onClick: " + MatchingAlgo.skillset.keySet());
                Log.d("LoginActivity", "onClick: " + searchBar.getText().toString());
                Log.d("LoginActivity", "onClick: " + MatchingAlgo.skillset.containsKey(searchBar.getText().toString()));
                if (!MatchingAlgo.getSkillset().containsKey(searchBar.getText().toString())) {
                    Toast.makeText(getContext(), "Please input a valid skill", Toast.LENGTH_SHORT).show();
                } else {
                    find_help_posts.clear();
                    String search_txt = searchBar.getText().toString().toLowerCase(); //lower case to account for all typing
                    //Matching Algo
                    //MatchingAlgo.getSkillset(Admin.getUserid(), search_txt)
                    MatchingAlgo matchingAlgo = new MatchingAlgo();
                    ArrayList<Map.Entry<String, Double>> algo_arr = matchingAlgo.skillsIsAllSmallCaps(Admin.getUserid(), search_txt);
                    Log.d("LoginActivity", "onClick: " + algo_arr);
                    for (Map.Entry<String, Double> algo_entry : algo_arr) {
                        String current_id = algo_entry.getKey();
                        int confidence_lvl = (int) Math.round(algo_entry.getValue());
                        String total_skills = "";

                        for (String all_skill : Social.getSkills(current_id).keySet()) {
                            total_skills = total_skills + all_skill + "\n";
                        }


                        find_help_posts.add(new Find_Help(Social.getName(current_id), Social.getPillar(current_id), confidence_lvl, Social.getDisplayPic(current_id), total_skills));

                    }
                }


                //Populating
               /*for (String id : Social.getName().keySet()){
                    if(Social.getSkills(id) != null) {
                        for (String skill : Social.getSkills(id).keySet()) {
                            if (skill.toLowerCase().equals(search_txt) ) {
                                String total_skills = "";
                                for(String all_skill : Social.getSkills(id).keySet()){
                                    total_skills = total_skills + all_skill + "\n";
                                }
                                find_help_posts.add(new Find_Help(Social.getName(id), Social.getPillar(id),Social.getDisplayPic(id),total_skills));
                            }
                        }
                    }
                }*/
                adapter.setPosts(find_help_posts);
                find_help_RecView.setAdapter(adapter);
            }
        });

    }
}