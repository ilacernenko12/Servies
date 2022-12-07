package com.example.servies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.servies.picasso.AvatarTransformation;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    private DatePickerDialog picker;
    private EditText date;
    private EditText name;
    private ImageView avatar;
    private Button submit;
    private static final int GALLERY_REQUEST = 1;

    private void init() {
        date = findViewById(R.id.date);
        date.setInputType(InputType.TYPE_NULL);
        avatar = findViewById(R.id.avatar);
        submit = findViewById(R.id.submit);
        name = findViewById(R.id.name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        date.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            picker = new DatePickerDialog(SignUpActivity.this,
                    (datePicker, year1, monthOfYear, dayOfMonth)
                            -> date.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year1), year, month, day);
            picker.show();
        });

        avatar.setOnClickListener(view -> {
            Intent photoGallery = new Intent(Intent.ACTION_GET_CONTENT);
            photoGallery.setType("image/*");
            startActivityForResult(Intent.createChooser(photoGallery, "Select picture"), GALLERY_REQUEST);
        });

        submit.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    // Download photo from gallery
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