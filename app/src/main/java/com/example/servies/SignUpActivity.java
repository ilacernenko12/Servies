package com.example.servies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.servies.picasso.AvatarTransformation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    // for realtime database
    private EditText username;
    private EditText mail;
    private EditText password;
    private EditText phoneNumber;
    private EditText repeatPass;

    private ImageView avatar;
    private Button submit;
    private static final int GALLERY_REQUEST = 1;
    private DatabaseReference appDataBase;
    private String USER_KEY = "Users";

    // REGEX FOR PASSWORD
    /*^               # start-of-string
    (?=.*[0-9])       # a digit must occur at least once
    (?=.*[a-z])       # a lower case letter must occur at least once
    (?=.*[A-Z])       # an upper case letter must occur at least once
    (?=\S+$)          # no whitespace allowed in the entire string
    .{8,}             # anything, at least eight places though
    $                 # end-of-string*/
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private void init() {
        avatar = findViewById(R.id.avatar);
        submit = findViewById(R.id.submit);
        username = findViewById(R.id.name);
        mail = findViewById(R.id.login);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phone);
        repeatPass = findViewById(R.id.password_valid);
        appDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        avatar.setOnClickListener(view -> {
            Intent photoGallery = new Intent(Intent.ACTION_GET_CONTENT);
            photoGallery.setType("image/*");
            startActivityForResult(Intent.createChooser(photoGallery, "Select picture"), GALLERY_REQUEST);
        });

        submit.setOnClickListener(view -> {
            String id = appDataBase.getKey();
            String name = username.getText().toString();
            String email = mail.getText().toString();
            String pass = password.getText().toString();
            String numberPhone = phoneNumber.getText().toString();
            String repeatPassword = repeatPass.getText().toString();

            Intent intent = new Intent(this, MainActivity.class);

            // field is empty? | do the passwords match? | regex for e-mail & password
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)
                    && !TextUtils.isEmpty(numberPhone)) {
                if (!email.matches(EMAIL_REGEX)) {
                    mail.setText("");
                    Toast.makeText(this, "E-mail is not valid", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(repeatPassword)) {
                    password.setText("");
                    repeatPass.setText("");
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (!pass.matches(PASSWORD_REGEX)){
                    password.setText("");
                    repeatPass.setText("");
                    Toast.makeText(this, "A-Z, a-z, 1-9 | 4-8 symbols", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(id, name, email, pass, numberPhone);
                    appDataBase.push().setValue(user);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "One of the input fields is not filled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Download photo from gallery |PICASSO|
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
                .into(avatar);
    }
}