package com.example.sutd_social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sutd_social.firebase.Bulletin;
import com.example.sutd_social.firebase.BulletinBoard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BulletinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BulletinFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BulletinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BulletinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BulletinFragment newInstance(String param1, String param2) {
        BulletinFragment fragment = new BulletinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //-------initialize----------
    private RecyclerView bulletinBoardRecView;
    private FloatingActionButton addPost;
    //private Button addPost;
    private ArrayList<BulletinBoardPost> bulletinBoardPosts;
    private BulletinBoardPostRecViewAdapter adapter;
    // for popup
    private final int activity_code = 1;
    //-----------
    //---------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bulletin, container, false);
        bulletinBoardRecView = view.findViewById(R.id.bulletin_board_post_RecView);

        bulletinBoardPosts = new ArrayList<>();

        adapter = new BulletinBoardPostRecViewAdapter(getContext());
        //firebase--------
        ArrayList<String> keySet = new ArrayList<>();
        HashMap<String, Bulletin> all_posts = BulletinBoard.getBulletin();
        for (String key : all_posts.keySet()){
            keySet.add(key);
        }


        for (String key : keySet ){
            String individual_post_title = BulletinBoard.getBulletin(key).title;
            String individual_post_description = BulletinBoard.getBulletin(key).description;
            bulletinBoardPosts.add(new BulletinBoardPost(individual_post_title,individual_post_description));
        }




        //--------



        adapter.setPosts(bulletinBoardPosts);
        bulletinBoardRecView.setAdapter(adapter);

        bulletinBoardRecView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        addPost = view.findViewById(R.id.btn_add_post);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bulletinBoardPosts.add(new BulletinBoardPost("title", "yoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyo"));

                startActivityForResult(new Intent(getContext(),BulletinPopUp.class),activity_code);

                //adapter.setPosts(bulletinBoardPosts);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == activity_code){
            if(resultCode == Activity.RESULT_OK){
                String txtTitle = data.getStringExtra("txtTitle");
                String txtDescription = data.getStringExtra("txtDescription");
                String txtDate = data.getStringExtra("txtDate");
//                textView.setText(str1);
//                textView2.setText(str2);
                if(txtTitle.length() == 0 && txtDescription.length() == 0){
                    Toast.makeText(getContext(),"Please fill in the Title and Description", Toast.LENGTH_SHORT).show();
                }

                else if(txtTitle.length() == 0){
                    Toast.makeText(getContext(),"Please fill in the Title", Toast.LENGTH_SHORT).show();
                }
                else if(txtDescription.length() == 0){
                    Toast.makeText(getContext(),"Please fill in the Description", Toast.LENGTH_SHORT).show();
                }
                else{
                    bulletinBoardPosts.add(new BulletinBoardPost(txtTitle, txtDescription,txtDate));
                    adapter.setPosts(bulletinBoardPosts);
                }


            }
        }
    }

}