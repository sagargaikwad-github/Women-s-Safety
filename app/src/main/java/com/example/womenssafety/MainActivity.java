package com.example.womenssafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

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
                String getPhone = sharedPreferences.getString("phone", "");
                sendMessage(getPhone);
            }
        });
    }

    private void sendMessage(String getPhone) {
        String Latitde = "18.4915";
        String Longitude = "73.8217";
        String message = "I am in Danger! Please Help me. \n\nLatitude : " + Latitde + " \nLongitude : " + Longitude +
                "\n\nLocate Me : " + Uri.parse("http://maps.google.com/maps?q=" + Latitde + "%2C" + Longitude);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto: " + getPhone));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(intent);
    }
}