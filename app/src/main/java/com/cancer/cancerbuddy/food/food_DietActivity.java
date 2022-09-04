package com.cancer.cancerbuddy.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cancer.cancerbuddy.MainActivity;
import com.cancer.cancerbuddy.R;

public class food_DietActivity extends AppCompatActivity {

    ImageView food1,food2;
    ImageView back_to_mainN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diet);

        back_to_mainN = findViewById(R.id.back_to_mainN);

        back_to_mainN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(food_DietActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        food1 = findViewById(R.id.food1);
        food2 = findViewById(R.id.food2);


        food1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(food_DietActivity.this, FoodActivity .class);
                startActivity(intent);
            }
        });

        food2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(food_DietActivity.this, RecipesActivity .class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent = new Intent(food_DietActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}