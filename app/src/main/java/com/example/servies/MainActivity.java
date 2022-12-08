package com.example.servies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText login;
    private EditText password;
    private Spinner spinner;
    private Button next;
    private Button signup;
    // for realtime database
    private DatabaseReference appDataBase;


    private void init() {
        login = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        spinner = findViewById(R.id.spinner);
        next = findViewById(R.id.nextButton);
        signup = findViewById(R.id.signUpButton);
        appDataBase = FirebaseDatabase.getInstance().getReference();
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
            validation();
        });
    }

    // get data from realtime database | validation check
    private void validation(){
        Intent intent1 = new Intent(this, FeedActivity.class);
        appDataBase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> arrayEmail = new ArrayList<>();
                ArrayList<String> arrayPassword = new ArrayList<>();
                ArrayList<String> arrayUsers = new ArrayList<>();

                String e = login.getText().toString();
                String p = password.getText().toString();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String userEmail = ds.child("email").getValue(String.class);
                    String userPassword = ds.child("password").getValue(String.class);
                    String users = ds.getValue().toString();

                    arrayEmail.add(userEmail);
                    arrayPassword.add(userPassword);

                    String nameByEmail = users.substring(users.indexOf("username=") + 9, users.indexOf('}'));

                    if (arrayEmail.contains(e) && arrayPassword.contains(p)){
                        intent1.putExtra("username", nameByEmail);
                       startActivity(intent1);
                       break;
                    } else {
                        Toast.makeText(MainActivity.this, "E-mail or password is not valid", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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