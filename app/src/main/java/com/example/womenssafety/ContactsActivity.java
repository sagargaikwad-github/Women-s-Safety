package com.example.womenssafety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ContactsActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText phoneET;
    Button saveBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        findIds();
        toolbarSetUp();
        buttonClicks();
    }

    private void buttonClicks() {
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneET.getText().toString().trim();
                if (phoneNumber.length() == 10) {
                    SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefContactList", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("phone", phoneET.getText().toString());
                    myEdit.commit();
                    Toast.makeText(ContactsActivity.this, "Contact saved for help", Toast.LENGTH_SHORT).show();
                    phoneET.getText().clear();
                } else {
                    Toast.makeText(ContactsActivity.this, "Please check phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findIds() {
        phoneET = findViewById(R.id.phoneET);
        saveBTN = findViewById(R.id.saveBTN);
        toolbar = findViewById(R.id.toolbar);
    }

    private void toolbarSetUp() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}