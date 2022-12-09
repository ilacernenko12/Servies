package com.example.servies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.servies.firebase.User;
import com.example.servies.picasso.AvatarTransformation;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;

public class SignUpActivity extends AppCompatActivity {
    private EditText username;
    private EditText mail;
    private EditText password;
    private EditText phoneNumber;
    private EditText repeatPass;
    private Button submit;
    private static final int GALLERY_REQUEST = 1;
    private Uri uploadUri;
    // firebase
    private DatabaseReference appDataBase;
    private String USER_KEY = "Users";
    private StorageReference storageReference;
    // regex
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private void init() {
        submit = findViewById(R.id.submit);
        username = findViewById(R.id.name);
        mail = findViewById(R.id.login);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phone);
        repeatPass = findViewById(R.id.password_valid);
        appDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        submit.setOnClickListener(view -> {saveData();});
    }

    private void saveData() {
        Intent intent = new Intent(this, MainActivity.class);
        String id = appDataBase.getKey();
        String name = username.getText().toString();
        String email = mail.getText().toString();
        String pass = password.getText().toString();
        String numberPhone = phoneNumber.getText().toString();
        String repeatPassword = repeatPass.getText().toString();

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
    }
}