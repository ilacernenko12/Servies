package com.example.servies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FeedActivity extends AppCompatActivity {
    private TextView username;

    private void init(){
        username = findViewById(R.id.username);
        username.setText(getIntent().getStringExtra("username"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        init();

    }
}