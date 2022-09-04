package com.cancer.cancerbuddy.Exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cancer.cancerbuddy.MainActivity;
import com.cancer.cancerbuddy.R;

public class ExerciseActivity extends AppCompatActivity {
    CardView mainT,mainT1,mainT3,mainT4,mainT5;

    ImageView back_to_mainN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        mainT = findViewById(R.id.mainT);
        mainT1 = findViewById(R.id.mainT1);

        mainT3 = findViewById(R.id.mainT3);
        mainT4 = findViewById(R.id.mainT4);
        mainT5 = findViewById(R.id.mainT5);

        back_to_mainN = findViewById(R.id.back_to_mainN);

        back_to_mainN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mainT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ExerciseActivity.this, ExerciseActivity1.class);
                startActivity(intent);

            }
        });
        mainT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ExerciseActivity.this, ExerciseActivity2.class);
                startActivity(intent);

            }
        });
        mainT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ExerciseActivity.this, ExerciseActivity3.class);
                startActivity(intent);

            }
        });
        mainT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ExerciseActivity.this, ExerciseActivity4.class);
                startActivity(intent);

            }
        });
        mainT5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ExerciseActivity.this, ExerciseActivity5.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}