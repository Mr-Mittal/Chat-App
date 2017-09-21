package com.example.yashmittal.chat;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Add extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    private Uri imguri;
    private FloatingActionButton FU,FC;

    public static final String Storage_Path = "image/";
    public static final int REQUEST_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        SelectImage();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Test");

        imageView = (ImageView) findViewById(R.id.imageView);
        FU = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        FU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadPhoto();
            }
        });
        FC = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        FC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add.this, MainActivity.class);
                startActivity(i);
            }
        });



    }

    @SuppressWarnings("VisibleForTests")
    private void UploadPhoto() {
        if (imguri != null)
        {
            final ProgressDialog Pd = new ProgressDialog(this);
            Pd.setTitle("Uploading Post");
            Pd.show();

            mStorageRef.child(Storage_Path + System.currentTimeMillis() + "." + getImageExt(imguri)).putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            Pd.dismiss();
                            Intent i = new Intent(Add.this,MainActivity.class);
                            startActivity(i);
                            FirebaseDatabase.getInstance().getReference("TestP").push().setValue(new ChatMessage( taskSnapshot.getDownloadUrl().toString() , FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Pd.dismiss();
                            Toast.makeText(getApplicationContext(), "UnSuccessfull", Toast.LENGTH_SHORT).show();

                        }
                    }) .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double  progess = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Pd.setMessage("Uploading  " + (int)progess + "" );
                }
            });

        }
        else {
            Toast.makeText(getApplicationContext(), "Please Select a Image", Toast.LENGTH_SHORT).show();

        }
    }

    private void SelectImage()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "SELECT IMAGE"), REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt (Uri uri){
        ContentResolver Cr = getContentResolver();
        MimeTypeMap MM = MimeTypeMap.getSingleton();
        return  MM.getExtensionFromMimeType(Cr.getType(uri));

    }


}
