package com.example.servies.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.servies.FeedActivity;
import com.example.servies.R;
import com.example.servies.firebase.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private ImageButton closeButton;
    private Button editButton;
    private Button saveButton;
    private View inflatedView;
    private EditText userBiography;
    private EditText userExperience;
    private EditText userCountry;
    private EditText userTechSkills;
    private EditText userContacts;
    private EditText userLinks;
    private DatabaseReference appDataBase;
    private String PROFILE_KEY = "Profile";
    private TextView username;

    private void init(){
        closeButton = inflatedView.findViewById(R.id.button_close_profile);
        editButton = inflatedView.findViewById(R.id.button_edit_profile);
        saveButton = inflatedView.findViewById(R.id.button_save_profile);
        userBiography = inflatedView.findViewById(R.id.user_biography);
        userExperience = inflatedView.findViewById(R.id.user_experience);
        userCountry = inflatedView.findViewById(R.id.user_country);
        userTechSkills = inflatedView.findViewById(R.id.user_skills);
        userContacts = inflatedView.findViewById(R.id.user_contact);
        userLinks = inflatedView.findViewById(R.id.user_network);
        appDataBase = FirebaseDatabase.getInstance().getReference(PROFILE_KEY);
        username = inflatedView.findViewById(R.id.profile_name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        closeButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        return inflatedView;
    }
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.button_close_profile:
                Intent intent=new Intent(getContext(),FeedActivity.class);
                startActivity(intent);
                break;
            case R.id.button_edit_profile:
                unblockEditText();
                break;
            case R.id.button_save_profile:
                sendDataToDB();
                blockEditText();
                break;
        }
    }

    private void sendDataToDB() {
        String id = appDataBase.getKey();
        String bio = userBiography.getText().toString();
        String exp = userExperience.getText().toString();
        String country = userCountry.getText().toString();
        String skills = userTechSkills.getText().toString();
        String contacts = userContacts.getText().toString();
        String links = userLinks.getText().toString();

        Profile profile = new Profile(id, bio, exp, country, skills, contacts, links);
        appDataBase.push().setValue(profile);
    }

    private void blockEditText() {
        editButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        userBiography.setFocusableInTouchMode(false);
        userBiography.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        userExperience.setFocusableInTouchMode(false);
        userExperience.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        userCountry.setFocusableInTouchMode(false);
        userCountry.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        userTechSkills.setFocusableInTouchMode(false);
        userTechSkills.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        userContacts.setFocusableInTouchMode(false);
        userContacts.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        userLinks.setFocusableInTouchMode(false);
        userLinks.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
    }

    private void unblockEditText() {
        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        userBiography.setFocusableInTouchMode(true);
        userBiography.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        userExperience.setFocusableInTouchMode(true);
        userExperience.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        userCountry.setFocusableInTouchMode(true);
        userCountry.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        userTechSkills.setFocusableInTouchMode(true);
        userTechSkills.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        userContacts.setFocusableInTouchMode(true);
        userContacts.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        userLinks.setFocusableInTouchMode(true);
        userLinks.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
    }
}