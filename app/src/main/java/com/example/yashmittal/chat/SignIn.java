package com.example.yashmittal.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private EditText SmName;
    private EditText SmPassword;
    private Button SLoginBtn;
    private FirebaseAuth SmAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SmAuth = FirebaseAuth.getInstance();
        SmName = (EditText) findViewById(R.id.SeditText);
        SmPassword = (EditText) findViewById(R.id.SeditText3);
        SLoginBtn = (Button) findViewById(R.id.Sbutton2);
        SLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSingin();

            }
        });

    }

    private void SSingin()
    {
        String Name = SmName.getText().toString();
        String FName = Name + "@Confession.com";
        String Password = SmPassword.getText().toString();
        if (TextUtils.isEmpty(FName) || TextUtils.isEmpty(Password))
        {

            Toast.makeText(SignIn.this, "Fields Are Emepty", Toast.LENGTH_SHORT).show();
        }
        else
        {

            SmAuth.signInWithEmailAndPassword(FName,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    {
                        Toast.makeText(SignIn.this, "Sign In Problem", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignIn.this, "Sign In Success", Toast.LENGTH_SHORT).show();
                        //FirebaseDatabase.getInstance().getReference("User").push()
                        Intent i = new Intent(SignIn.this,MainActivity.class);
                        startActivity(i);
                    }

                }
            });
        }}
}

