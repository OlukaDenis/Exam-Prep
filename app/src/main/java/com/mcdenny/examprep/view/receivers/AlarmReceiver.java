package com.mcdenny.examprep.view.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.utils.AppUtils;
import com.mcdenny.examprep.view.activity.HomeActivity;

import static com.mcdenny.examprep.utils.Constants.ALARM_NOTIFICATION_ID;
import static com.mcdenny.examprep.utils.Constants.PRIMARY_CHANNEL_ID;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager  = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        deliverNotification(context);
    }

    /* Alarm manager */
    public void deliverNotification(Context context){
        Intent contentIntent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                ALARM_NOTIFICATION_ID,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = AppUtils.getNotificationBuilder(context,
                                                "Prepare for Microverse",
                                               "Don't forget to join the call and study for the Google exam!");
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.ic_alarm_notification);
        notificationManager.notify(ALARM_NOTIFICATION_ID, builder.build());
    }
}
