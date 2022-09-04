package com.cancer.cancerbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.util.Calendar;

public class TrackSyntomActivity2 extends AppCompatActivity {

    Slider slider;
    TextView MMp21;
    float value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_syntom2);

        MMp21 =findViewById(R.id.MMp21);
        slider =findViewById(R.id.continuousSlider);

        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {

                 value = slider.getValue();

                MMp21.setText(""+ value);



            }
        });

      /*  MMp21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  AddCalendarEvent();

              //  startActivity(new Intent(this, WeekViewActivity.class));

                Intent intent = new Intent(TrackSyntomActivity2.this, WeekViewActivity.class);
                startActivity(intent);
            }
        });*/





    }

    private void AddCalendarEvent() {
        Calendar calendarEvent = Calendar.getInstance();
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra("beginTime", calendarEvent.getTimeInMillis());
        i.putExtra("allDay", true);
        i.putExtra("rule", "FREQ=YEARLY");
        i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
        i.putExtra("title", "Calendar Event");
        startActivity(i);

    }





}