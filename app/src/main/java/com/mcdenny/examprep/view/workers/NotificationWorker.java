package com.mcdenny.examprep.view.workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.utils.AppUtils;
import com.mcdenny.examprep.view.activity.SettingsActivity;

import static com.mcdenny.examprep.utils.Constants.PRIMARY_CHANNEL_ID;
import static com.mcdenny.examprep.utils.Constants.WORKER_CHANNEL_ID;
import static com.mcdenny.examprep.utils.Constants.WORKER_NOTIFICATION_ID;

public class NotificationWorker  extends Worker {
    private static final String WORK_TIME = "executed_time";
    private NotificationManager notificationManager;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data taskData = getInputData();
        String taskDataString = taskData.getString(SettingsActivity.MESSAGE_STATUS);

        showNotification(taskDataString != null ? taskDataString : "Message has been Sent");

        //Sending the data to the caller
        Data outputData = new Data.Builder()
                .putString(WORK_TIME, ""+System.currentTimeMillis())
                .build();
        //Sending the work status to the caller
        return Result.success(outputData);
    }

    private void showNotification(String message) {
        notificationManager = (NotificationManager)
                getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //create the notification channel for the app
        createWorkerNotificationChannel();

        Intent workerIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        PendingIntent workerPendingIntent = PendingIntent.getActivity(getApplicationContext(),
                WORKER_NOTIFICATION_ID,
                workerIntent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = getNotificationBuilder(getApplicationContext(),
                                                "Work Manager",
                                                        message);
        builder.setContentIntent(workerPendingIntent);

        notificationManager.notify(WORKER_NOTIFICATION_ID, builder.build());
    }


    private void createWorkerNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Notification channel targeting Android 8 and above
            NotificationChannel channel = new NotificationChannel(WORKER_CHANNEL_ID,
                    "Worker Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.BLUE);
            channel.setDescription("Notification from the worker");

            notificationManager.createNotificationChannel(channel);
        }
    }



    private NotificationCompat.Builder getNotificationBuilder(Context context, String task, String desc){
        return new NotificationCompat.Builder(context, WORKER_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentTitle(task)
                .setContentText(desc)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
    }

}
