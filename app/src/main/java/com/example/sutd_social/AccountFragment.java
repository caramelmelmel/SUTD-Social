package com.example.sutd_social;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.sutd_social.firebase.Admin;
import com.example.sutd_social.firebase.Social;
import com.example.sutd_social.firebase.User;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ImageView displayPic;
    private Button saveButton;
    private EditText username, bio, inputSkills, fifthRows, teleContact;
    private ChipGroup skillGroup, pillarGroup;
    public AccountFragment() {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        displayPic = view.findViewById(R.id.profile_picture);
        username = view.findViewById(R.id.username);
        bio = view.findViewById(R.id.edit_bio);
        inputSkills = view.findViewById(R.id.add_skills);
        fifthRows = view.findViewById(R.id.edit_fifth_rows);
        teleContact = view.findViewById(R.id.edit_contact);
        pillarGroup = view.findViewById(R.id.edit_pillar);
        skillGroup = view.findViewById(R.id.skills_display);
        saveButton = view.findViewById(R.id.save_profile);

        // Show DisplayPic
        String url = Social.getDisplayPic(Admin.getUserid());
        Social.displayImage(getActivity(), url, view.findViewById(R.id.profile_picture));

        // Get image from user
        View profile_pic = view.findViewById(R.id.profile_picture);
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 10);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == getActivity().RESULT_OK) {
                Uri imageUri = data.getData();
                Social.addImage(Admin.getUserid(), imageUri);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // display image here
        User user = Social.getUser(Admin.getUserid());
        username.setText(user.name);
        bio.setText(user.info);
        fifthRows.setText(user.fifthRow);
        teleContact.setText(user.telegram);

        String pillar = user.pillar;
        if(pillar != null){
        switch (pillar) {
            case "Freshmore": {
                Chip pillarChip = view.findViewById(R.id.freshmore_chip);
                pillarChip.setChecked(true);
                break;
            }
            case "ASD": {
                Chip pillarChip = view.findViewById(R.id.asd_chip);
                pillarChip.setChecked(true);
                break;
            }
            case "EPD": {
                Chip pillarChip = view.findViewById(R.id.epd_chip);
                pillarChip.setChecked(true);
                break;
            }
            case "ESD": {
                Chip pillarChip = view.findViewById(R.id.esd_chip);
                pillarChip.setChecked(true);
                break;
            }
            case "ISTD": {
                Chip pillarChip = view.findViewById(R.id.istd_chip);
                pillarChip.setChecked(true);
                break;
            }
        }}

        HashMap<String, Long> skills = user.skills;
        if(user.skills==null){
            return;
        }
        if (!skills.isEmpty()) {
            for (String skill : skills.keySet()) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                Chip newChip = (Chip) inflater.inflate(R.layout.skill_chip, null);
                newChip.setText(skill);
                skillGroup.addView(newChip);

                newChip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Confidence Level");

                        EditText input = new EditText(getContext());
                        String skillName = newChip.getText().toString();
                        input.setHint(skills.get(skillName).toString());
                        input.setGravity(Gravity.CENTER);
                        builder.setView(input);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String inText = input.getText().toString();
                                if (inText == "") {
                                    // Do nothing
                                    return;
                                } else {
                                    int integer = Integer.parseInt(inText);
                                    if (integer > 0 && integer < 100) {
                                        skills.put(skillName, new Long(input.getText().toString()));
                                    }
                                }
                            }
                        });
                        builder.show();
                    }
                });

                newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        skills.remove(newChip.getText().toString());
                        skillGroup.removeView(newChip);
                    }
                });
            }
        }

        inputSkills.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                Chip newChip = (Chip) inflater.inflate(R.layout.skill_chip, null);
                newChip.setText(inputSkills.getText().toString());
                skillGroup.addView(newChip);
                skills.put(inputSkills.getText().toString(), new Long(50));
                inputSkills.setText("");

                newChip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Confidence Level");

                        EditText input = new EditText(getContext());
                        String skillName = newChip.getText().toString();
                        input.setHint(skills.get(skillName).toString());
                        input.setGravity(Gravity.CENTER);
                        builder.setView(input);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String inText = input.getText().toString();
                                int integer = Integer.parseInt(inText);
                                if (integer > 0 && integer < 100) {
                                    skills.put(skillName, new Long(input.getText().toString()));
                                }
                            }
                        });
                        builder.show();
                    }
                });

                newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        skills.remove(newChip.getText().toString());
                        skillGroup.removeView(newChip);
                    }
                });

                return false;
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = username.getText().toString();
                if (user_name != null) {
                    Social.setAttr("Name", Admin.getUserid(), user_name);
                }

                String info = bio.getText().toString();
                if (info != null) {
                    Social.setAttr("Info", Admin.getUserid(), info);
                }

                String fifth_row = fifthRows.getText().toString();
                if (fifth_row != null) {
                    Social.setAttr("FifthRow", Admin.getUserid(), fifth_row);
                }

                String telegram = teleContact.getText().toString();
                if (telegram != null) {
                    Social.setAttr("Telegram", Admin.getUserid(), telegram);
                }

                Integer checkedId = pillarGroup.getCheckedChipId();
                if (checkedId != -1) {
                    Chip pillarChip = pillarGroup.findViewById(checkedId);
                    String studentPillar = pillarChip.getText().toString();
                    Social.setAttr("Pillar", Admin.getUserid(), studentPillar);
                }

                Social.setAttr("Skills", Admin.getUserid(), skills);

                Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
