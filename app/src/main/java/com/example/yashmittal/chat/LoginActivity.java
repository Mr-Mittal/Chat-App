package com.example.yashmittal.chat;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mPassword;
    private Button LoginBtn;
    private Button MSignUp;
    private FirebaseAuth mAuth;
    //private EditText mUName;
    //String UName= mUName.getText().toString().trim();
    //String EID = mName.getText().toString().trim();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mAuth = FirebaseAuth.getInstance();
        mName = (EditText) findViewById(R.id.editText);
       // mUName = (EditText) findViewById(R.id.editText2);

        mPassword = (EditText) findViewById(R.id.editText3);
        MSignUp = (Button) findViewById(R.id.button6);
        MSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignIn.class);
                startActivity(i);
            }
        });
        LoginBtn = (Button) findViewById(R.id.button2);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login();
            }
        });

    }

    private void Login()
    {
        String Name = mName.getText().toString();
        String FName = Name + "@Confession.com";
        String Password = mPassword.getText().toString();
        if (TextUtils.isEmpty(FName) || TextUtils.isEmpty(Password))
        {

            Toast.makeText(LoginActivity.this, "Fields Are Emepty", Toast.LENGTH_SHORT).show();
        }
        else
        {

            mAuth.createUserWithEmailAndPassword(FName,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this, "Sign In Problem", Toast.LENGTH_SHORT).show();
                }
                else {

                    //UpdateProfile();
                   // FirebaseDatabase.getInstance().getReference("User").push().setValue(new UserDetails(UName , FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getUid()));
                    Toast.makeText(LoginActivity.this, "Sign In Success", Toast.LENGTH_SHORT).show();
                    //FirebaseDatabase.getInstance().getReference("User").push()
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                }

            }
        });
    }}

   /*private void UpdateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()

                .setDisplayName(UName)
                //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    //}
}
