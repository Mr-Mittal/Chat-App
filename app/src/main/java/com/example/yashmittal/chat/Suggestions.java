package com.example.yashmittal.chat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PrivateKey;
import java.util.HashMap;

public class Suggestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText Sugg = (EditText) findViewById(R.id.editText4);
        final TextView Thank = (TextView) findViewById(R.id.textView3);
        final String Suggestion = null;

        Button Send = (Button) findViewById(R.id.button3);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Sugg.getText().toString().trim().length() == 0)
                    Toast.makeText(Suggestions.this, "Enter The Text", Toast.LENGTH_SHORT).show();
                else {
                    String Sug = Sugg.getText().toString().trim();
                    String use = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    HashMap<String, String> Map1 = new HashMap();
                    Map1.put("Suggestion", Sug);
                    Map1.put("User", use);
                    FirebaseDatabase.getInstance().getReference("Suggestions").push().setValue(Map1);

                   // FirebaseDatabase.getInstance().getReference("Suggestions").push().setValue(new ChatMessage(Sugg.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), null));

                    Sugg.setText(" ");
                    Thank.setVisibility(View.VISIBLE);
                }

            }
        });

    }

}
