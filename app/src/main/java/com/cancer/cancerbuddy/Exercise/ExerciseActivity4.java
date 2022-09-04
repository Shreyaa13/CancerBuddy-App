package com.cancer.cancerbuddy.Exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cancer.cancerbuddy.R;

public class ExerciseActivity4 extends AppCompatActivity {

    ImageView back_to_mainN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise4);


        back_to_mainN = findViewById(R.id.back_to_mainN);

        back_to_mainN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ExerciseActivity4.this, ExerciseActivity.class);
                startActivity(intent);
                finish();


            }
        });
    }
}