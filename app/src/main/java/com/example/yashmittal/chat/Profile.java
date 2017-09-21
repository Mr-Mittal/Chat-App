package com.example.yashmittal.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profile extends AppCompatActivity {

    Button NeewFeed , Add, Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        NeewFeed = (Button) findViewById(R.id.button5);
        Add = (Button) findViewById(R.id.button7);
        Profile = (Button) findViewById(R.id.button8);

        NeewFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,MainActivity.class);
                startActivity(i);
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,Add.class);
                startActivity(i);
            }
        });
    }
}
