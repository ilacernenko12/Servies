package com.example.servies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.servies.fragments.ProfileFragment;
import com.example.servies.fragments.SettingFragment;

public class FeedActivity extends AppCompatActivity {
    private TextView username;
    private ImageView ivAvatar;
    private ImageButton button_profile;
    private ImageButton button_setting;

    private void init(){
        username = findViewById(R.id.username);
        username.setText(getIntent().getStringExtra("username"));
        ivAvatar = findViewById(R.id.user_avatar);
        button_profile = findViewById(R.id.button_profile);
        button_setting = findViewById(R.id.setting);
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

        button_setting.setOnClickListener(view -> {
            SettingFragment settingFragment = new SettingFragment();
            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
            ft1.replace(R.id.profile_container,settingFragment);
            ft1.commit();
        });
    }
}