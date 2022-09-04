package com.cancer.cancerbuddy.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cancer.cancerbuddy.MainActivity;
import com.cancer.cancerbuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TextView Pname, Pmobile,Pgender,CancerD,Page,PCountry;

    private CircleImageView Pprofile;
    private CardView CardProfileSetup;
    ImageView PostP,back_to_mainN;

    private FirebaseAuth mAuth;
    private String CurrentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference firebaseReference;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_profile);

        //check Internet Connection
        //   new CheckInternetConnection(this).checkConnection();

        dialog = new Dialog(ProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_wait1);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

            }
        }, 3000);
        
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseReference = firebaseDatabase.getReference().child( "Users" ).child(CurrentUser  );
        Pmobile = findViewById( R.id.Pmobile );
        Pname = findViewById( R.id.Pname );
        Pgender = findViewById( R.id.Pgender );
  /*      profileCreateSetup = findViewById( R.id.profileCreateSetup );
        profileUpdateSetup = findViewById( R.id.profileUpdateSetup );
        CardProfileSetup = findViewById( R.id.CardProfileSetup );*/

        PostP = findViewById( R.id.PostP );
        Pprofile = findViewById( R.id.Pprofile );
        CancerD = findViewById( R.id.CancerD );
        Page = findViewById( R.id.Page );
        back_to_mainN = findViewById( R.id.back_to_mainN );
        PCountry = findViewById( R.id.PCountry );

        back_to_mainN.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( ProfileActivity.this, MainActivity.class );
                startActivity( i );
                finish();
            }
        } );

        //redirect user to update profile page
        PostP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileUpdateActivity.class);
                startActivity(intent);
            }
        });

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SendUserToMainActivity();

    }

    private void SendUserToMainActivity()
    {
        Intent loginIntent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(loginIntent);

    }


    //display profile credentials
    @Override
    protected void onStart() {
        super.onStart();
        firebaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {

                    dialog.dismiss();


                    String Full_Name =dataSnapshot.child( "Full_Name" ).getValue().toString();
                    String Mobile_No =dataSnapshot.child( "Mobile_No" ).getValue().toString();
                    String Pcountry_Code =dataSnapshot.child( "CountryCode" ).getValue().toString();
                    String Cancer_Stage =dataSnapshot.child( "Cancer_Stage" ).getValue().toString();
                    String Age =dataSnapshot.child( "Age" ).getValue().toString();

                    String Gender =dataSnapshot.child( "Gender" ).getValue().toString();


                    //  Picasso.get().load( profileimage ).into( Profile );
                    Pname.setText(Full_Name );
                    Pmobile.setText( Mobile_No );
                    PCountry.setText( Pcountry_Code );
                    CancerD.setText( Cancer_Stage );
                    Page.setText(Age );

                    Pgender.setText(  Gender );


                }
                else
                {
                    dialog.dismiss();


                    Pprofile.setVisibility( View.INVISIBLE );
                    Pname.setVisibility( View.INVISIBLE );
                    Pmobile.setVisibility( View.INVISIBLE );
                    CancerD.setVisibility( View.INVISIBLE );
                    Page.setVisibility( View.INVISIBLE );
                    PCountry.setVisibility( View.INVISIBLE );

                    Pgender.setVisibility( View.INVISIBLE );

                    Toast.makeText(ProfileActivity.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );




    }

    @Override
    protected void onResume() {
        super.onResume();

        //check Internet Connection
        //  new CheckInternetConnection(this).checkConnection();
    }



}
