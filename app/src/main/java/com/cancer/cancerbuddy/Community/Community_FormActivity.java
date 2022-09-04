package com.cancer.cancerbuddy.Community;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import com.cancer.cancerbuddy.MainActivity;
import com.cancer.cancerbuddy.Profile.MakeProfileActivity;
import com.cancer.cancerbuddy.Profile.ProfileActivity;
import com.cancer.cancerbuddy.Profile.ProfileUpdateActivity;
import com.cancer.cancerbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Community_FormActivity extends AppCompatActivity {

    ImageView back_to_mainN;

    private RecyclerView ShowPostInRecy;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<CommModel> commModels;
    private CommAdapter commAdapters;

    FloatingActionButton add_Post;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_form);

        back_to_mainN = findViewById(R.id.back_to_mainN);
        add_Post = findViewById(R.id.add_Post);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserID );

        back_to_mainN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Community_FormActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ShowPostInRecy = findViewById( R.id.ShowPostRecycler );
        ShowPostInRecy.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        ShowPostInRecy.setLayoutManager( layoutManager );

        //button to add new post
        add_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(Community_FormActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_wait1);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        CheckUser();
                    }
                }, 3000);
            }
        });
        LoadAllPost();
    }

    //send user to make a new post page
    private void CheckUser() {
        UsersRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Intent intent  = new Intent(Community_FormActivity.this, CommPostActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
                else
                {
                    Intent intent  = new Intent(Community_FormActivity.this, ProfileUpdateActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
    }

    //load all community posts
    private void LoadAllPost() {
        commModels = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Community" );
        reference
                .addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        commModels.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            CommModel SModel = ds.getValue( CommModel.class );
                            commModels.add( SModel );
                        }
                        commAdapters = new CommAdapter( Community_FormActivity.this, commModels );
                        ShowPostInRecy.setAdapter( commAdapters );
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                } );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Community_FormActivity.this, MainActivity.class);
        startActivity(intent);
    }
}