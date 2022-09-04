package com.cancer.cancerbuddy.food;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.cancer.cancerbuddy.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class FoodActivity extends AppCompatActivity {

    ImageView foodRe,foodRe1,back_to_mainN;
    TextView link,link1;
    Dialog setDesc;
    Dialog dialog,dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        back_to_mainN = findViewById(R.id.back_to_mainN);

        back_to_mainN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FoodActivity.this, food_DietActivity.class);
                startActivity(intent);
                finish();
            }
        });

        foodRe = findViewById(R.id.foodRe);
        foodRe1 = findViewById(R.id.foodRe1);
        link = findViewById(R.id.link);
        link1 = findViewById(R.id.link1);

        //using special library class to zoom in/out image
        PhotoViewAttacher pAttacher,pAttacher1;
        pAttacher = new PhotoViewAttacher(foodRe);
        pAttacher1 = new PhotoViewAttacher(foodRe1);
        pAttacher.update();
        pAttacher1.update();
     /*   SetEditDialog = findViewById(R.id.food_desc);*/
        //setContentView(R.layout.food_desc);
      //  ImageView img = alertDialog.findViewById(R.id.backToHide);

        dialog = new Dialog(FoodActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.food_desc);
        dialog.setCanceledOnTouchOutside(false);

        ImageView img= dialog.findViewById(R.id.backToHide);

        dialog1 = new Dialog(FoodActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.food_desc1);
        dialog1.setCanceledOnTouchOutside(false);

        ImageView img1= dialog1.findViewById(R.id.backToHide);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }
}