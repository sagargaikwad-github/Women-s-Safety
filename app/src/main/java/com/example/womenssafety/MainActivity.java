package com.example.womenssafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    private ArrayList<PhoneModel> phoneNumberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);


        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent);
                return false;
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefContactList", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("phone", null);
                Type type = new TypeToken<ArrayList<PhoneModel>>() {
                }.getType();
                phoneNumberList = new ArrayList<>();
                phoneNumberList = gson.fromJson(json, type);
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        //SITS : 18.454572231183818, 73.82008556609185
        String Latitde = "18.454572231183818";
        String Longitude = "73.82008556609185";

        if (phoneNumberList != null) {
            if (phoneNumberList.size() > 0)
                for (int i = 0; i < phoneNumberList.size(); i++) {
                    String message = "hey " + phoneNumberList.get(i).getName() + ", I am in Danger! Please Help me. \n\nLatitude : " + Latitde + " \nLongitude : " + Longitude +
                            "\n\nLocate Me : " + Uri.parse("http://maps.google.com/maps?q=" + Latitde + "%2C" + Longitude);
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto: " + phoneNumberList.get(i).getPhone()));
                    intent.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(intent);
                }
            else {
                String message = "I am in Danger! Please Help me. \n\nLatitude : " + Latitde + " \nLongitude : " + Longitude +
                        "\n\nLocate Me : " + Uri.parse("http://maps.google.com/maps?q=" + Latitde + "%2C" + Longitude);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto: " + ""));
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(intent);
            }
        } else {
            String message = "I am in Danger! Please Help me. \n\nLatitude : " + Latitde + " \nLongitude : " + Longitude +
                    "\n\nLocate Me : " + Uri.parse("http://maps.google.com/maps?q=" + Latitde + "%2C" + Longitude);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto: " + ""));
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(intent);
        }
    }
}