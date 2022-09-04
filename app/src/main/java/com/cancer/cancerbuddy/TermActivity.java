package com.cancer.cancerbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class TermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TermActivity.this, MainActivity.class);
        startActivity(intent);
    }
}