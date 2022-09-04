package com.cancer.cancerbuddy.Record_Sys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cancer.cancerbuddy.MainActivity;
import com.cancer.cancerbuddy.R;

public class RecordActivity2 extends AppCompatActivity {

    ImageView viewOnCalender,viewOnGraph;
    ImageView back_to_mainN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record2);

        back_to_mainN = findViewById(R.id.back_to_mainN);

        back_to_mainN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecordActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewOnCalender = findViewById(R.id.viewOnCalender);
        viewOnGraph = findViewById(R.id.viewOnGraph);

        //redirect user to view calendar page
        viewOnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity2.this, TrackSyntomActivity.class);
                startActivity(intent);
            }
        });

        //redirect user to view graph page
        viewOnGraph.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity2.this, GraphChatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(RecordActivity2.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}