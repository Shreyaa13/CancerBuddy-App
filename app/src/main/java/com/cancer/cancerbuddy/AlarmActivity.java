package com.cancer.cancerbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.cancer.cancerbuddy.medicine.AlarmReceiver;
import com.cancer.cancerbuddy.medicine.Medicine_ManagementActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AlarmActivity extends AppCompatActivity {

   // private ActivityMainBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    TextView selectedTime;
    Button selectTimeBtn,setAlarmBtn,cancelAlarmBtn;

    MediaPlayer music;

    String IdGet;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Intent intent= getIntent();
        IdGet=intent.getStringExtra("Id");

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserID );



        selectedTime = findViewById(R.id.selectedTime);
        selectTimeBtn = findViewById(R.id.selectTimeBtn);
        setAlarmBtn = findViewById(R.id.setAlarmBtn);
        cancelAlarmBtn = findViewById(R.id.cancelAlarmBtn);


       /* binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/
        createNotificationChannel();

        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePicker();
                setAlarmBtn.setVisibility(View.VISIBLE);

            }
        });

        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAlarm();

            }
        });

       cancelAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelAlarm();

            }
        });

    }



    private void cancelAlarm() {

        Intent intent = new Intent(this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        music.stop();

        if (alarmManager == null){

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            music.stop();



        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();

        music.stop();
    }

    private void setAlarm() {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);

        Toast.makeText(this, selectedTime.getText(), Toast.LENGTH_SHORT).show();

        sendAdapter();

        HashMap userMap = new HashMap();
      //  userMap.put( "Id", IdGet );
        userMap.put( "Alarm", selectedTime.getText() );


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child( "Alarm" );
        reference.child(currentUserID).child(IdGet).child("Alarm")
        .updateChildren( userMap ).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            Toast.makeText( AlarmActivity.this, "your Alarm Set Successfully.", Toast.LENGTH_LONG ).show();

                        }
                    }, 3000);


                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText( AlarmActivity.this, "Error Occured: " + message, Toast.LENGTH_SHORT ).show();

                }
            }
        } );
    }









    private void sendAdapter() {
        Intent intent = new Intent(AlarmActivity.this, Medicine_ManagementActivity.class);
        intent.putExtra("setAlarm",selectedTime.getText());
        startActivity(intent);
    }

    private void showTimePicker() {

        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();

        picker.show(getSupportFragmentManager(),"foxandroid");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (picker.getHour() > 12){

                 selectedTime.setText(
                            String.format("%02d",(picker.getHour()-12))+" : "+String.format("%02d",picker.getMinute())+" PM"
                    );

                }else {

                    selectedTime.setText(picker.getHour()+" : " + picker.getMinute() + " AM");

                }

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);

            }
        });


    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "foxandroidReminderChannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("cancer_buddy",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);



            Date currentTime = Calendar.getInstance().getTime();
            String setAlarmTime = selectedTime.getText().toString();

            if (setAlarmTime.equals(currentTime)) {
                MediaPlayer music = MediaPlayer.create(
                        this, R.raw.wake);
            }

             /*music = MediaPlayer.create(
                    this, R.raw.wake);

            music.start();*/

        }


    }

}