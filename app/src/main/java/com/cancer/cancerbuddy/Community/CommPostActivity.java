package com.cancer.cancerbuddy.Community;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cancer.cancerbuddy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CommPostActivity extends AppCompatActivity {

    EditText descPost;
    ImageView imgComm,back_to_mainN;
    Button PostC;
    TextView Date_and_TimeP,NameCP;

    private static final  int CAMERA_REQUEST_CODE = 200;
    private static final  int STORAGE_REQUEST_CODE = 300;
    private static final  int IMAGE_PICK_GALLERY_CODE = 400;
    private static final  int IMAGE_PICK_CAMERA_CODE = 500;

    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri image_uri;

    private FirebaseAuth mAuth;
    private DatabaseReference CommRef,UsersRef;
    String currentUserID;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm_post);

        back_to_mainN = findViewById(R.id.back_to_mainN);

        back_to_mainN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CommPostActivity.this, Community_FormActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        CommRef = FirebaseDatabase.getInstance().getReference().child( "Community" );
        UsersRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserID );

        descPost = findViewById(R.id.descPost);
        imgComm = findViewById(R.id.imgComm);
        PostC = findViewById(R.id.PostC);
        NameCP = findViewById(R.id.NameCP);

        PostC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();

                dialog = new Dialog(CommPostActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_wait1);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        } );

        LoadProfileInfo();

        //add image from gallery requires permissions
        imgComm.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                ShowImagePickDialog();
            }
        } );
        cameraPermissions= new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions= new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    }

    String Full_Name;
    //loading profile details for display
    private void LoadProfileInfo()
    {
        UsersRef
                .addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            // load data
                             Full_Name =""+snapshot.child( "Full_Name" ).getValue();
                            NameCP.setText("Hi "+ Full_Name );
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                } );
    }

    private String DescPost;

    //adding description in post
    private void inputData()
    {
        DescPost = descPost.getText().toString().trim();

        if (TextUtils.isEmpty( DescPost ))
        {
            Toast.makeText( this, "Please Write Something.." ,Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;

        }
        else {
/*
            loadingBar.setTitle("Saving Information");
            loadingBar.setMessage("Please wait, Product Added Successfully...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);*/
            PostSocial();
        }
    }

    //adding post to database
    private void PostSocial()
    {
        final String PostId= ""+System.nanoTime();
        String saveCurrentDate, saveCurrentTime;
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat( " dd/MM/yyyy" );
        saveCurrentDate = currentDate.format( calendar.getTime() );
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat( "HH:mm:ss a" );
        saveCurrentTime = currentTime.format( calendar.getTime() );
        if (image_uri == null)
        {
            HashMap<String, Object> hashMap = new HashMap<>(  );
            hashMap.put( "PostId", ""+ PostId);
            hashMap.put( "User_ID", ""+ currentUserID);
            hashMap.put( "User_Name", ""+ Full_Name);
            hashMap.put( "Desc", ""+ DescPost);
            hashMap.put( "Date", ""+ saveCurrentDate + " " +saveCurrentTime);
            hashMap.put( "Image", "");
            CommRef.child(PostId).updateChildren( hashMap )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                          //  Toast.makeText( CommPostActivity.this, "Post Added" ,Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            SendCommForm();
                           // clearData();
                          //  loadingBar.dismiss();
                        }
                    } )
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( CommPostActivity.this, ""+e.getMessage() ,Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                           // loadingBar.dismiss();
                        }
                    } );
        }
        else
        {
            String filePathAndName = "Comm_Image/" + "" +PostId  ;
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile( image_uri )
                    .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadImageUri = uriTask.getResult();
                            if (uriTask.isSuccessful())
                            {
                                HashMap<String, Object> hashMap = new HashMap<>(  );
                                hashMap.put( "PostId", ""+ PostId);
                                hashMap.put( "User_ID", ""+ currentUserID);
                                hashMap.put( "User_Name", ""+ Full_Name);
                                hashMap.put( "Desc", ""+ DescPost);
                                hashMap.put( "Date", ""+ saveCurrentDate + " " +saveCurrentTime);
                                hashMap.put( "Image", ""+downloadImageUri);
                                CommRef.child(PostId).updateChildren( hashMap )
                                        .addOnSuccessListener( new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                               // Toast.makeText( CommPostActivity.this, "Post Added" ,Toast.LENGTH_SHORT).show();
                                              //  clearData();
                                              //  loadingBar.dismiss();
                                                SendCommForm();
                                                dialog.dismiss();
                                            }
                                        } )
                                        .addOnFailureListener( new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText( CommPostActivity.this, ""+e.getMessage() ,Toast.LENGTH_SHORT).show();
                                                //loadingBar.dismiss();
                                                dialog.dismiss();
                                            }
                                        } );
                            }
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( CommPostActivity.this, ""+e.getMessage() ,Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    } );

        }
    }

    private void SendCommForm() {

        Intent intent = new Intent(CommPostActivity.this, Community_FormActivity.class);
        startActivity(intent);
        finish();
    }

    //image pick dialog
    private void ShowImagePickDialog()
    {
        String[] options = {"Camera" , "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( "Pick Image" )
                .setItems( options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i ==0)
                        {
                            if (CheckCameraPermission())
                            {
                                PickFromCamera();
                            }
                            else
                            {
                                RequestCameraPermission();
                            }
                        }
                        else
                        {
                            if (CheckStoragePermission())
                            {
                                PickFromGallery();
                            }
                            else
                            {
                                RequestStoragePermission();
                            }
                        }

                    }
                } ).show();
    }

    private void PickFromGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType( "image/*" );
        startActivityForResult( intent, IMAGE_PICK_GALLERY_CODE );

    }

    private void PickFromCamera()
    {
        ContentValues contentValues = new ContentValues() ;
        contentValues.put( MediaStore.Images.Media.TITLE, "Temp_Image_Title" );
        contentValues.put( MediaStore.Images.Media.DESCRIPTION, "Temp_Image_Description" );

        image_uri = getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues );

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE  );
        intent.putExtra( MediaStore.EXTRA_OUTPUT, image_uri );
        startActivityForResult( intent, IMAGE_PICK_CAMERA_CODE );
    }

    private boolean CheckStoragePermission()
    {
        boolean result = ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE )== (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private  void RequestStoragePermission()
    {
        ActivityCompat.requestPermissions( this, storagePermissions, STORAGE_REQUEST_CODE );
    }
    private boolean CheckCameraPermission()
    {
        boolean result = ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA ) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission( this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void RequestCameraPermission ()
    {
        ActivityCompat.requestPermissions( this, cameraPermissions, CAMERA_REQUEST_CODE );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case CAMERA_REQUEST_CODE :
            {
                if (grantResults.length>0)
                {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                 /*   if (cameraAccepted && storageAccepted)
                    {
                        PickFromCamera();
                    }
                    else
                    {*/
                        Toast.makeText( this, " Camera & Storage permission are required .." ,Toast.LENGTH_SHORT).show();
                  //  }
                }

            }
            case  STORAGE_REQUEST_CODE :
            {
                if (grantResults.length>0)
                {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                   /* if ( storageAccepted)
                    {*/
                        PickFromGallery();
                /*    }
                    else
                    {
                        Toast.makeText( this, "Storage permission are required .." ,Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
        }
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
        {
            if (requestCode== IMAGE_PICK_GALLERY_CODE)
            {
                image_uri = data.getData();
                imgComm.setImageURI( image_uri );
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE)
            {
                imgComm.setImageURI( image_uri );
            }
        }
        super.onActivityResult( requestCode, resultCode, data );
    }


}