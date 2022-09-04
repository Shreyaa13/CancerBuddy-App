package com.cancer.cancerbuddy.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cancer.cancerbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileUpdateActivity extends AppCompatActivity  {

    private EditText FullName, MobileNo,CancerDesc,Ageid,CouId;

    private CardView Card_setup_button;

    private RadioGroup setup_Radio_Group;
    private RadioButton setup_for_radiobtn;
    String strGender;

    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.activity_profile_update );

        dialog = new Dialog(ProfileUpdateActivity.this);
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
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserID );

        FullName =  findViewById( R.id.setup_fullname );
        MobileNo =  findViewById( R.id.setup_mobile );
        CancerDesc =  findViewById( R.id.CancerDesc );
        Ageid =  findViewById( R.id.Ageid );
        CouId =  findViewById( R.id.CouId );

        setup_Radio_Group =  findViewById( R.id.setup_Radio_Group );
        Card_setup_button =  findViewById( R.id.Card_setup_button );
        // ProfileImage = (CircleImageView) findViewById( R.id.setup_profile );
        loadingBar = new ProgressDialog( this );

        LoadProfileInfo();

        setup_Radio_Group.setOnCheckedChangeListener( (group, i) -> {
            setup_for_radiobtn = setup_Radio_Group.findViewById( i );
            switch (i) {
                case R.id.setup_radiobtn_male:
                    strGender = setup_for_radiobtn.getText().toString();
                    break;
                case R.id.setup_radiobtn_female:
                    strGender = setup_for_radiobtn.getText().toString();
                    break;
                default:
            }

        } );

        //  Card_setup_button.setOnClickListener( view -> SaveAccountSetupInformation() );
        Card_setup_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAccountSetupInformation();
            }
        } );
    }

    private void LoadProfileInfo()
    {
        dialog.dismiss();
        UsersRef
                .addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            // load data
                            String Full_Name =""+snapshot.child( "Full_Name" ).getValue();
                            String Mobile_No =""+snapshot.child( "Mobile_No" ).getValue();

                            String Gender =""+snapshot.child( "Gender" ).getValue();

                            // set Data

                            FullName.setText( Full_Name );
                            MobileNo.setText( Mobile_No );

                            // setup_Radio_Group.setText( Pin_code );

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                } );
    }


    private void SaveAccountSetupInformation()
    {
        String fullname = FullName.getText().toString();
        String mobile = MobileNo.getText().toString();
        String cancerStage1 = CancerDesc.getText().toString();
        String age = Ageid.getText().toString();
        String countryCode = CouId.getText().toString();

        String pgender = strGender;

        if (fullname.isEmpty() || mobile.isEmpty()  || mobile.isEmpty()  ||countryCode.isEmpty() || age.isEmpty() || setup_Radio_Group.getCheckedRadioButtonId() == -1) {
            Toast.makeText( ProfileUpdateActivity.this, "Please Fill Your Data ", Toast.LENGTH_SHORT ).show();
            dialog.dismiss();

        }
        else
        {
                dialog.show();

                HashMap userMap = new HashMap();
                userMap.put( "Full_Name", fullname );
                userMap.put( "Mobile_No", mobile );
                userMap.put( "CountryCode", countryCode );
                userMap.put( "Gender", pgender );
                userMap.put( "Cancer_Stage", cancerStage1 );
                userMap.put( "Age", age );

                UsersRef.updateChildren( userMap ).addOnCompleteListener( new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    SendUserToMainActivity();
                                    Toast.makeText( ProfileUpdateActivity.this, "your Account is Updated Successfully.", Toast.LENGTH_LONG ).show();
                                    dialog.dismiss();
                                }
                            }, 3000);

                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText( ProfileUpdateActivity.this, "Error Occured: " + message, Toast.LENGTH_SHORT ).show();
                            dialog.dismiss();
                        }
                    }
                } );
            }

    }
    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(ProfileUpdateActivity.this, ProfileActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}
