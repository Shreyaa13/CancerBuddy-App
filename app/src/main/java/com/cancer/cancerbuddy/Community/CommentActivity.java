package com.cancer.cancerbuddy.Community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cancer.cancerbuddy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView ShowComment;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<CommentModel> commModels;
    private CommentAdapter commAdapters;

    EditText AddComm;
    ImageView PostComm;

    private FirebaseAuth mAuth;
    private DatabaseReference CommRef,UsersRef;
    String currentUserID;
    String getPostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent myIntent = getIntent();
        getPostId = myIntent.getStringExtra("Id");

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        AddComm = findViewById(R.id.AddComm);
        PostComm = findViewById(R.id.PostComm);

        ShowComment = findViewById( R.id.ShowPostRecycler );
        ShowComment.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        ShowComment.setLayoutManager( layoutManager );
        LoadAllPost();

        PostComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });
    }

    //load all comments
    private void LoadAllPost() {
        commModels = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Post" ).child("Comment");
        reference.child(getPostId)
                // .child( ShopId ).child( "Item" )
                .addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        commModels.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            CommentModel SModel = ds.getValue( CommentModel.class );
                            commModels.add( SModel );
                        }
                        commAdapters = new CommentAdapter( CommentActivity.this, commModels );
                        ShowComment.setAdapter( commAdapters );
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                } );

    }

    private String DescPost;
    //typing comments
    private void inputData()
    {
        DescPost = AddComm.getText().toString().trim();
        if (TextUtils.isEmpty( DescPost ))
        {
            Toast.makeText( this, "Please Write Something.." ,Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            PostSocial();
        }
    }

    //storing comment to database
    private void PostSocial()
    {
        final String PostId= ""+System.nanoTime();

        String saveCurrentDate, saveCurrentTime;
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat( " dd/MM/yyyy" );
        saveCurrentDate = currentDate.format( calendar.getTime() );
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat( "HH:mm:ss a" );
        saveCurrentTime = currentTime.format( calendar.getTime() );

            HashMap<String, Object> hashMap = new HashMap<>(  );
            hashMap.put( "PostId", ""+ PostId);
            hashMap.put( "UserId", ""+ currentUserID);
            hashMap.put( "Comm", ""+ DescPost);
            hashMap.put( "Date", ""+ saveCurrentDate + " " +saveCurrentTime);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Post" ).child("Comment");

        reference.child(getPostId).child(PostId).updateChildren( hashMap )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //  Toast.makeText( CommPostActivity.this, "Post Added" ,Toast.LENGTH_SHORT).show();

                            //  loadingBar.dismiss();
                        }
                    } )
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( CommentActivity.this, ""+e.getMessage() ,Toast.LENGTH_SHORT).show();

                        }
                    } );
    }
}