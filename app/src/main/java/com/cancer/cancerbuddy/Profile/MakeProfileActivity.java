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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cancer.cancerbuddy.MainActivity;
import com.cancer.cancerbuddy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MakeProfileActivity extends AppCompatActivity {


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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_profile);









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






    private void SaveAccountSetupInformation()
    {

        String fullname = FullName.getText().toString();
        String mobile = MobileNo.getText().toString();
        String cancerStage1 = CancerDesc.getText().toString();
        String age = Ageid.getText().toString();
        String countryCode = CouId.getText().toString();

        String pgender = strGender;


        if (fullname.isEmpty() || mobile.isEmpty() || countryCode.isEmpty() || cancerStage1.isEmpty() || age.isEmpty() || setup_Radio_Group.getCheckedRadioButtonId() == -1) {
            Toast.makeText( MakeProfileActivity.this, "Please Fill Your Data ", Toast.LENGTH_SHORT ).show();
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
                                    Toast.makeText( MakeProfileActivity.this, "your Account is Updated Successfully.", Toast.LENGTH_LONG ).show();
                                    dialog.dismiss();
                                }
                            }, 3000);


                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText( MakeProfileActivity.this, "Error Occured: " + message, Toast.LENGTH_SHORT ).show();
                            dialog.dismiss();
                        }
                    }
                } );
            }

        }




    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(MakeProfileActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}
