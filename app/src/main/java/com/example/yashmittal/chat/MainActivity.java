package com.example.yashmittal.chat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.firebase.ui.FirebaseUI;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.messaging.*;
import com.squareup.picasso.Picasso;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.internal.framed.FrameReader;

import static com.example.yashmittal.chat.R.id.imageView;
import static com.example.yashmittal.chat.R.menu.main_menu;

public class MainActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private static int a =0;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_main;
    FloatingActionButton Send;
    Button NeewFeed , Add, Profile;
    private String Model;
    private DatabaseReference mDataBase;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== R.id.suggestion)
        {
            Intent i = new Intent(MainActivity.this, Suggestions.class);
            startActivity(i);


        }

        if(item.getItemId()== R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_main,"Successfully Signed Out", Snackbar.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                    //finish();
                }
            });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {

                displaychat();
            }
            else
            {
                Snackbar.make(activity_main,"We Could Not Sign You In", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirstTime();
        FirebaseMessaging.getInstance().subscribeToTopic("android");
        setContentView(R.layout.activity_main);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);

        Model = Build.MODEL;
        LoginCheck();
        //NeewFeed = (Button) findViewById(R.id.button5);
        Add = (Button) findViewById(R.id.button7);
       // Profile = (Button) findViewById(R.id.button8);

/*        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Profile.class);
                startActivity(i);
            }
        });*/
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Add.class);
                startActivity(i);
            }
        });

         Send = (FloatingActionButton) findViewById(R.id.Send);
       Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Input = (EditText) findViewById(R.id.Input);
                if (Input.getText().toString().trim().length() == 0)
                    Toast.makeText(MainActivity.this, "Enter The Text", Toast.LENGTH_SHORT).show();
                else {
                    FirebaseDatabase.getInstance().getReference("Test").push().setValue(new ChatMessage(Input.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), Model));

                    Input.setText(" ");
                    }
            }
        });



    }
    private void LoginCheck() {
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {

            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivityForResult(i, SIGN_IN_REQUEST_CODE);

        }
        else

        {
            ProgressDialog();

            Snackbar.make(activity_main,"Welcome" + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_SHORT).show();
            displaychat();
        }

    }



    private void FirstTime() {
        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstrun){
            final Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.updatebox);
            dialog.setTitle("Title...");


            TextView text = (TextView) dialog.findViewById(R.id.textView);
            text.setText("        Whats New :    \n\n\n" + "->  Introducing Chat Bubbles \n" + "->  New Section For Suggestion Has Been In Menu Bar \n" + "->  All The Major Bugs Has Been Fixed" );


            Button dialogButton = (Button) dialog.findViewById(R.id.button4);

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("firstrun", false)
                    .commit();
        }
    }

    private void displaychat() {
        FirstTime();


        ListView ListOfMsg = (ListView) findViewById(R.id.ListOfMsg);
       // ListOfMsg.setSelection(ListOfMsg.getAdapter().getCount()-1);
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R.layout.listitem, FirebaseDatabase.getInstance().getReference("Test") ) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                TextView MsgUser,MsgTym,MsgText;
                ImageView ImgView;


                ImgView = (ImageView) v.findViewById(R.id.Msg_Image);
                MsgUser = (TextView) v.findViewById(R.id.Msg_User);
                MsgText = (TextView) v.findViewById(R.id.Msg_Text);
                MsgUser.setTextColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                MsgTym = (TextView)  v.findViewById(R.id.Msg_Tym);

               /* if (model.getURL()== )
                {
                    MsgText.setText(model.getMsgText());
                }
                else {
                    Picasso.with(getApplicationContext()).load(model.getURL()).into(ImgView);
                }*/
               Picasso.with(getApplicationContext()).load(model.getURL()).into(ImgView);
                MsgUser.setText(model.getMsgUser());
                MsgText.setText(model.getMsgText());
                MsgTym.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", model.getMsgTym()));


            }
        };
        ListOfMsg .setAdapter(adapter);

       /* ListView ListOfMsgP = (ListView) findViewById(R.id.ListOfMsg);
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R.layout.listitem, FirebaseDatabase.getInstance().getReference("TestP") ) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                TextView MsgUser,MsgTym,MsgText;
                ImageView ImgView;


                ImgView = (ImageView) v.findViewById(R.id.Msg_Image);
                MsgUser = (TextView) v.findViewById(R.id.Msg_User);
               // MsgText = (TextView) v.findViewById(R.id.Msg_Text);
                MsgUser.setTextColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                MsgTym = (TextView)  v.findViewById(R.id.Msg_Tym);

               /* if (model.getURL()== )
                {
                    MsgText.setText(model.getMsgText());
                }
                else {
                    Picasso.with(getApplicationContext()).load(model.getURL()).into(ImgView);
                }
                Picasso.with(getApplicationContext()).load(model.getURL()).into(ImgView);
                MsgUser.setText(model.getMsgUser());
               // MsgText.setText(model.getMsgText());
                MsgTym.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", model.getMsgTym()));


            }
        };
        ListOfMsgP .setAdapter(adapter);*/
    }




    void ProgressDialog (){

        if(a==0){
        final ProgressDialog dialog = ProgressDialog.show(this, "Please Wait", "Loading Confessions !!!",
                true);
        dialog.show();
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                dialog.dismiss();
            }
        }.start();
        a++;}

    }

}
