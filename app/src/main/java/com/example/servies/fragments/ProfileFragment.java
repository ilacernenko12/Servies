package com.example.servies.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.servies.picasso.AvatarTransformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private ImageButton closeButton;
    private Button editButton;
    private Button saveButton;
    private ImageButton chooseAvatar;
    private View inflatedView;
    private EditText userBiography;
    private EditText userExperience;
    private EditText userCountry;
    private EditText userTechSkills;
    private EditText userContacts;
    private EditText userLinks;
    private DatabaseReference appDataBase;
    private String PROFILE_KEY = "Profile";
    private TextView userName;
    private static final String USERNAME = "username";
    private final int GALLERY_REQUEST = 1;

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
        userName = inflatedView.findViewById(R.id.profile_username);
        chooseAvatar = inflatedView.findViewById(R.id.profile_avatar);
        chooseAvatar.setEnabled(false);
    }

    public static ProfileFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString(USERNAME, name);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*String name = getArguments().getString(USERNAME);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        return inflatedView;
    }

    @Override
    public void onStart() {
        super.onStart();
        closeButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        chooseAvatar.setOnClickListener(this);
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
                getDataFromDb(userBiography, userExperience, userCountry,
                        userTechSkills, userContacts, userLinks);
                blockEditText();
                break;
            case R.id.profile_avatar:
                getPhotoFromGallery();
                break;
        }
    }

    private void getPhotoFromGallery(){
        Intent photoGallery = new Intent(Intent.ACTION_GET_CONTENT);
        photoGallery.setType("image/*");
        startActivityForResult(Intent.createChooser(photoGallery, "Select picture"), GALLERY_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri = data.getData();
        Picasso.get()
                .load(imageUri)
                .placeholder(R.drawable.icon_error_picasso) // when photo is loading
                .error(R.drawable.icon_error_picasso) // if don't download photo
                .transform(new AvatarTransformation())
                .resize(400, 400)
                .centerCrop()
                .into(chooseAvatar);
    }

    private void getDataFromDb(EditText uBiography, EditText uExperience, EditText uCountry,
                               EditText uTechSkills, EditText uContacts, EditText uLinks) {
        String b =  uBiography.getText().toString();
        String e =  uExperience.getText().toString();
        String cou = uCountry.getText().toString();
        String ts = uTechSkills.getText().toString();
        String con = uContacts.getText().toString();
        String l = uLinks.getText().toString();

        appDataBase.child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> arrayBio = new ArrayList<>();
                ArrayList<String> arrayExp = new ArrayList<>();
                ArrayList<String> arrayCountry = new ArrayList<>();
                ArrayList<String> arraySkills = new ArrayList<>();
                ArrayList<String> arrayContacts = new ArrayList<>();
                ArrayList<String> arrayLinks = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String bio = ds.child("bio").getValue(String.class);
                    String exp = ds.child("exp").getValue(String.class);
                    String country = ds.child("country").getValue(String.class);
                    String skills = ds.child("skills").getValue(String.class);
                    String contacts = ds.child("contacts").getValue(String.class);
                    String links = ds.child("links").getValue(String.class);

                    arrayBio.add(bio);
                    arrayExp.add(exp);
                    arrayCountry.add(country);
                    arraySkills.add(skills);
                    arrayContacts.add(contacts);
                    arrayLinks.add(links);

                    if (arrayBio.contains(b) && arrayExp.contains(e) && arrayCountry.contains(cou)
                    && arraySkills.contains(ts) && arrayContacts.contains(con) && arrayLinks.contains(l)){
                        uBiography.setText(bio);
                        uExperience.setText(exp);
                        uCountry.setText(country);
                        uTechSkills.setText(skills);
                        uContacts.setText(contacts);
                        uLinks.setText(links);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        chooseAvatar.setEnabled(false);
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
        chooseAvatar.setEnabled(true);
    }
}