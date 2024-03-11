package com.example.womenssafety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class ContactsActivity extends AppCompatActivity implements deleteContact {
    Toolbar toolbar;
    TextInputEditText phoneET, nameET;
    Button saveBTN;
    ArrayList<PhoneModel> phoneNumberList;
    RecyclerView contactRV;
    TextView noContactsFoundTV,contactListTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        findIds();
        toolbarSetUp();
        buttonClicks();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Load all data from sharedPreference
        loadData();
    }

    private void buttonClicks() {
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneUserName = nameET.getText().toString().trim();
                String phoneNumber = phoneET.getText().toString().trim();
                if (phoneNumber.length() == 10) {
                    addToPhoneList();
                } else {
                    Toast.makeText(ContactsActivity.this, "Please check phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addToPhoneList() {
        if (phoneNumberList != null) {
            if (phoneNumberList.size() > 4) {
                Toast.makeText(this, "Old phone deleted", Toast.LENGTH_SHORT).show();
                phoneNumberList.remove(0);
                storePhone();
            } else {
                storePhone();
            }
        } else {
            storePhone();
        }

    }

    private void storePhone() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefContactList", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        if (phoneNumberList == null) {
            phoneNumberList = new ArrayList<>();
        }
        phoneNumberList.add(new PhoneModel(nameET.getText().toString().trim(), phoneET.getText().toString().trim()));
        Gson gson = new Gson();
        String json = gson.toJson(phoneNumberList);
        myEdit.putString("phone", json);
        myEdit.apply();
        loadData();
        Toast.makeText(ContactsActivity.this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
        phoneET.getText().clear();
        nameET.getText().clear();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefContactList", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("phone", null);
        Type type = new TypeToken<ArrayList<PhoneModel>>() {
        }.getType();
        phoneNumberList = new ArrayList<>();
        phoneNumberList = gson.fromJson(json, type);

        if (phoneNumberList != null) {
            if (phoneNumberList.size() > 0) {
                noContactsFoundTV.setVisibility(View.GONE);
                contactListTitle.setVisibility(View.VISIBLE);
                contactRV.setVisibility(View.VISIBLE);
                contactRV.setLayoutManager(new LinearLayoutManager(this));
                Collections.reverse(phoneNumberList);
                ContactAdapter contactAdapter = new ContactAdapter(phoneNumberList, this, this);
                contactRV.setAdapter(contactAdapter);
            } else {
                noContactsFoundTV.setVisibility(View.VISIBLE);
                contactListTitle.setVisibility(View.GONE);
                contactRV.setVisibility(View.GONE);
            }
        } else {
            noContactsFoundTV.setVisibility(View.VISIBLE);
            contactListTitle.setVisibility(View.GONE);
            contactRV.setVisibility(View.GONE);
        }
    }

    private void findIds() {
        nameET = findViewById(R.id.nameET);
        phoneET = findViewById(R.id.phoneET);
        saveBTN = findViewById(R.id.saveBTN);
        toolbar = findViewById(R.id.toolbar);
        contactRV = findViewById(R.id.contactRV);
        noContactsFoundTV = findViewById(R.id.noContactsFoundTV);
        contactListTitle = findViewById(R.id.contactListTitle);
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

    @Override
    public void deleteContact(int position) {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefContactList", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        phoneNumberList.remove(position);
        Gson gson = new Gson();
        String json = gson.toJson(phoneNumberList);
        myEdit.putString("phone", json);
        myEdit.apply();
        loadData();
    }
}