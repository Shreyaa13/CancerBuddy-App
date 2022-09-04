package com.cancer.cancerbuddy.Record_Sys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cancer.cancerbuddy.MainActivity;
import com.cancer.cancerbuddy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Track_SystemActivity extends AppCompatActivity {

    private RecyclerView Track_record;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<TrackRecordModel> TrackRecordModels;
    private TrackRecordAdapter TrackRecordAdapter;

    private FirebaseAuth mAuth;
    private DatabaseReference CommRef, UsersRef;
    String currentUserID;

    LinearLayout checkChart;

    ImageView ShowCalender, HideCalender;

    private RelativeLayout rl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_system);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        CommRef = FirebaseDatabase.getInstance().getReference().child("TrackRecord");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        checkChart = findViewById(R.id.checkChart);
        ShowCalender = findViewById(R.id.ShowCalender);
        HideCalender = findViewById(R.id.HideCalender);
        rl1 = findViewById(R.id.rl1);

        ShowCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideCalender.setVisibility(View.VISIBLE);
                rl1.setVisibility(View.VISIBLE);

                ShowCalender.setVisibility(View.GONE);
            }
        });

        checkChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Track_SystemActivity.this, RecordActivity2.class);
                startActivity(intent);
            }
        });


        HideCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideCalender.setVisibility(View.GONE);
                rl1.setVisibility(View.GONE);

                ShowCalender.setVisibility(View.VISIBLE);
            }
        });


        Track_record = findViewById( R.id.Track_record );
        Track_record.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        Track_record.setLayoutManager( layoutManager );

        LoadAllRecord();

  /*      GoTime=findViewById(R.id.GoTime);
      //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());
        GoTime.setText(currentDateandTime);*/
    }

    //display all symptom categories from database
    private void LoadAllRecord() {
        TrackRecordModels = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Infection" );
        reference
                // .child( ShopId ).child( "Item" )
                .addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TrackRecordModels.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            TrackRecordModel SModel = ds.getValue( TrackRecordModel.class );
                            TrackRecordModels.add( SModel );
                        }
                        TrackRecordAdapter = new TrackRecordAdapter( Track_SystemActivity.this, TrackRecordModels );
                        Track_record.setAdapter( TrackRecordAdapter );
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                } );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Track_SystemActivity.this, MainActivity.class);
        startActivity(intent);
    }
}