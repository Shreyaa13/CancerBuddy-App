package com.cancer.cancerbuddy.Record_Sys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cancer.cancerbuddy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TrackSyntomActivity extends AppCompatActivity {

    CalendarView calendar;
    TextView date_view;
    FloatingActionButton add_taskCalender;

    private RecyclerView ShowTrackRecord;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<TModel> tModels;
    private TAdapter tAdapter;

    private FirebaseAuth mAuth;
    private DatabaseReference CommRef,UsersRef;
    String currentUserID;

    ImageView back_to_mainN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_syntom);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
       /* CommRef = FirebaseDatabase.getInstance().getReference().child( "Community" );
        UsersRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserID );
        */

        // By ID we can use each component
        // which id is assign in xml file
        // use findViewById() to get the
        // CalendarView and TextView
        add_taskCalender = findViewById(R.id.add_taskCalender);
        calendar = findViewById(R.id.SysCalender);
        date_view = findViewById(R.id.date_view);
        back_to_mainN = findViewById(R.id.back_to_mainN);


        back_to_mainN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(TrackSyntomActivity.this,  RecordActivity2.class );
                startActivity(intent);
                finish();
            }
        });


        // Add Listener in calendar for when date selcted is changed
        calendar
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override
                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {
                                // Store the value of date with
                                // format in String type Variable
                                // Add 1 in month because month
                                // index is start with 0
                                String Date
                                        ="0"+ dayOfMonth + "/0"
                                        + (month + 1) + "/" + year;
                                // set this date in TextView for Display
                                date_view.setText(Date);
                                String txt = date_view.getText().toString();
                                if (txt.isEmpty())
                                {
                                    LoadAllRecord();
                                }
                                else
                                {
                                    LoadAllRecord1();
                                }

                            }
                        });


        ShowTrackRecord = findViewById( R.id.ShowEvent );
        ShowTrackRecord.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        ShowTrackRecord.setLayoutManager( layoutManager );
        LoadAllRecord();

    }

    //display all recorded symptoms of current date
    private void LoadAllRecord() {
        String saveCurrentDate;
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat( " dd/MM/yyyy" );
        saveCurrentDate = currentDate.format( calendar.getTime() );
        tModels = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "TrackRecord" ).child(currentUserID);
        reference.orderByChild("Date").equalTo(saveCurrentDate)
                // .child( ShopId ).child( "Item" )
                .addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tModels.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            TModel SModel = ds.getValue( TModel.class );
                            tModels.add( SModel );
                        }
                        tAdapter = new TAdapter( TrackSyntomActivity.this, tModels );
                        ShowTrackRecord.setAdapter( tAdapter );
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                } );
    }

    //display all recorded symptoms for date selected
    private void LoadAllRecord1() {

        String txt1 = date_view.getText().toString();

        String saveCurrentDate;
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat( " dd/MM/yyyy" );
        saveCurrentDate = currentDate.format( calendar.getTime() );

        tModels = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "TrackRecord" ).child(currentUserID);
        reference.orderByChild("Date").equalTo(txt1)
                // .child( ShopId ).child( "Item" )
                .addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tModels.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            TModel SModel = ds.getValue( TModel.class );

                            tModels.add( SModel );

                        }
                        tAdapter = new TAdapter( TrackSyntomActivity.this, tModels );
                        ShowTrackRecord.setAdapter( tAdapter );

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                } );

    }

    @Override
    public void onBackPressed() {
        Intent  intent = new Intent(TrackSyntomActivity.this,  RecordActivity2.class );
        startActivity(intent);
        finish();
    }
}