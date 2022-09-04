package com.cancer.cancerbuddy.medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cancer.cancerbuddy.MainActivity;
import com.cancer.cancerbuddy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Medicine_ManagementActivity extends AppCompatActivity {

    ImageView back_to_mainN,PostNM;
    TextView scheme_new_id;
    Dialog dialog;

    TextView txt_1,txt_2;
    CardView cardreg;
    Button btn_reg1;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;
    FloatingActionButton mAddFab;

    String setAlarm;

    private RecyclerView medicineRecy;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<MedicineModel> MedicineModels;
    private MedicineAdapter MedicineAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_management);

        Intent intent = getIntent();
        setAlarm = intent.getStringExtra("setAlarm");

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child( "Medicine_Management" ).child( currentUserID );

        mAddFab = findViewById(R.id.add_fab);

        medicineRecy = findViewById( R.id.medicineRecy );
        medicineRecy.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        medicineRecy.setLayoutManager( layoutManager );

        back_to_mainN = findViewById(R.id.back_to_mainN);
        PostNM = findViewById(R.id.PostNM);
        scheme_new_id = findViewById(R.id.scheme_new_id);
        dialog = new Dialog(Medicine_ManagementActivity.this);

        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickPost();
            }
        });

        LoadAllPost();

    }

    private void ClickPost() {
        dialog.setContentView(R.layout.dialog_post_medicine);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        txt_1 = dialog.findViewById(R.id.txt_1);
        txt_2 = dialog.findViewById(R.id.txt_2);
        btn_reg1 = dialog.findViewById(R.id.btn_reg1);

        btn_reg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });
        dialog.show();
    }

    private String pdtxt1,pdtxt2,pdtxt3;
    //input medicine data
    private void inputData()
    {
        pdtxt1 = txt_1.getText().toString().trim();
        pdtxt2 = txt_2.getText().toString().trim();
        if (TextUtils.isEmpty( pdtxt1 ))
        {
            Toast.makeText( this, "Please enter medicine name..." ,Toast.LENGTH_SHORT).show();
            return;
        }
       else if (TextUtils.isEmpty( pdtxt2 ))
        {
            Toast.makeText( this, "Please enter medicine description.." ,Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            PostDataMM();
        }
    }

    //store data to database
    private void PostDataMM()
    {
        final String PostId= ""+System.nanoTime();
        HashMap<String, Object> hashMap = new HashMap<>(  );
        hashMap.put( "txtId", ""+ PostId);
        hashMap.put( "txtA", ""+ pdtxt1);
        hashMap.put( "txtB", ""+ pdtxt2);
       // hashMap.put( "Alarm", ""+ "NA");
       // hashMap.put( "Date", ""+ saveCurrentDate + " " +saveCurrentTime);
       // hashMap.put( "Image", "");
        UsersRef.child(PostId).updateChildren( hashMap )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //  Toast.makeText( CommPostActivity.this, "Post Added" ,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText( Medicine_ManagementActivity.this, ""+e.getMessage() ,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } );
    }

    //load all medicine details from database
    private void LoadAllPost() {
        MedicineModels = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Medicine_Management" );
        reference.child(currentUserID)
                   .addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MedicineModels.clear();
                        //  String productCategory = ""+ds.child( "Category" ).getValue();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            MedicineModel SModel = ds.getValue( MedicineModel.class );
                            // ShopProductModel SModel = snapshot.getValue(ShopProductModel.class);
                            MedicineModels.add( SModel );
                        }
                        MedicineAdapters = new MedicineAdapter( Medicine_ManagementActivity.this, MedicineModels );
                        medicineRecy.setAdapter( MedicineAdapters );
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                } );
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        dialog.dismiss();

        Intent intent = new Intent(Medicine_ManagementActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}