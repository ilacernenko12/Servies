package com.example.servies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText login;
    private EditText password;
    private Spinner spinner;
    private Button next;
    private Button signup;

    private void init() {
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        spinner = findViewById(R.id.spinner);
        next = findViewById(R.id.nextButton);
        signup = findViewById(R.id.signUpButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choose_role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        signup.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        next.setOnClickListener(view -> {
            Intent intent = new Intent(this, FeedActivity.class);
            startActivity(intent);
        });
    }
    // If on click spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}