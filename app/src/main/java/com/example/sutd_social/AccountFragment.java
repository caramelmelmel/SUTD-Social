package com.example.sutd_social;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.sutd_social.R;
import com.example.sutd_social.firebase.Admin;
import com.example.sutd_social.firebase.Social;
import com.google.android.material.chip.Chip;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
    //init views-------
    private EditText editText_skills, editText_pillar;
    private Button btn_save;
    //---------
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
//        editText_pillar = view.findViewById(R.id.edit_pillar);  // chips?
//        editText_skills = view.findViewById(R.id.add_skills); // skill edit text
//        btn_save = view.findViewById(R.id.save_profile);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //String student_skill = editText_skills.getText().toString();
//                //String student_pillar = editText_pillar.getText().toString();
////                if (!student_pillar.isEmpty()){
////                    Social.setAttr("Pillar", Admin.getUserid(), student_pillar);
////                }
////                if (!student_skill.isEmpty()){
////                    Social.setAttr("Skills", Admin.getUserid(), student_skill);
////                }
//            }
//        });
    }

//    private MainViewModel mViewModel;
//    public static AccountFragment newInstance() {
//        return new AccountFragment();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
//
//        Chip skill1 = getView().findViewById(R.id.skill_chip1);
//    }
}