package com.example.servies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.servies.fragments.ProfileFragment;
import com.squareup.picasso.Picasso;

public class FeedActivity extends AppCompatActivity {
    private TextView username;
    private ImageView ivAvatar;
    private ImageButton button_profile;

    private void init(){
        username = findViewById(R.id.username);
        username.setText(getIntent().getStringExtra("username"));
        ivAvatar = findViewById(R.id.user_avatar);
        button_profile = findViewById(R.id.button_profile);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        init();

        button_profile.setOnClickListener(view -> {
            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.profile_container, profileFragment);
            ft.commit();
        });
    }
}