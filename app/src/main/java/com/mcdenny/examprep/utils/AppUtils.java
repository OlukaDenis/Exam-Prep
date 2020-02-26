package com.mcdenny.examprep.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.mcdenny.examprep.R;

import static com.mcdenny.examprep.utils.Constants.PRIMARY_CHANNEL_ID;
import static com.mcdenny.examprep.utils.Constants.WORKER_CHANNEL_ID;

public class AppUtils {

    private AppUtils(){}

    public static void createNotificationChannel(NotificationManager notificationManager){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Notification channel targeting Android 8 and above
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.BLUE);
            channel.setDescription("Notification from Mascot");

            notificationManager.createNotificationChannel(channel);
        }
    }

    public static NotificationCompat.Builder getNotificationBuilder(Context context, String task, String desc){
        return new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle(task)
                .setContentText(desc)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
    }
}
