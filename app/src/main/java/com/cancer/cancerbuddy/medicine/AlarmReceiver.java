package com.cancer.cancerbuddy.medicine;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cancer.cancerbuddy.MainActivity;
import com.cancer.cancerbuddy.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,i,0);

        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.wake);

        //future development for receiving alarm notifications
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"cancer_buddy")
                .setSmallIcon(R.drawable.ic_launcher_background)
               // .setContentTitle("Foxandroid Alarm Manager")
               // .setContentText("Subscribe for android related content")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
              //  .setSound(NotificationCompatR.raw.butt_buddy)
                .setSound(sound)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        MediaPlayer mp= MediaPlayer.create(context, R.raw.wake);
        mp.start();
        notificationManagerCompat.notify(123,builder.build());

    }


}
