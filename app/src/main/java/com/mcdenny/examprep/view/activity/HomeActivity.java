package com.mcdenny.examprep.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.model.Movie;
import com.mcdenny.examprep.utils.AppUtils;
import com.mcdenny.examprep.utils.Constants;
import com.mcdenny.examprep.view.adapters.MovieAdapter;
import com.mcdenny.examprep.view.receivers.AlarmReceiver;
import com.mcdenny.examprep.viewmodel.MovieViewModel;

import java.util.List;

import static com.mcdenny.examprep.utils.Constants.ACTION_UPDATE_NOTIFICATION;
import static com.mcdenny.examprep.utils.Constants.ALARM_NOTIFICATION_ID;
import static com.mcdenny.examprep.utils.Constants.NOTIFICATION_ID;
import static com.mcdenny.examprep.utils.Constants.PRIMARY_CHANNEL_ID;

public class HomeActivity extends AppCompatActivity {
    private Button notify, update, cancel;
    private MovieViewModel viewModel;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    private NotificationManager notificationManager;
    private NotificationReceiver mReceiver = new NotificationReceiver();
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Movies");


        viewModel = new ViewModelProvider(this, new MovieViewModel.MovieViewModelFactory(this.getApplication()))
                    .get(MovieViewModel.class);

        adapter = new MovieAdapter();
        recyclerView = findViewById(R.id.trending_recycler_view);
        recyclerView.setAdapter(adapter);

        //listen to data changes and pass it to adapter for displaying in recycler view
        viewModel.moviesPagedList.observe(this, pagedList -> {
            adapter.submitList(pagedList);
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);



        notify = findViewById(R.id.notify);
        notify.setOnClickListener(v -> sendNotification());

        //Start the notification manager
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //create the notification channel for the app
        AppUtils.createNotificationChannel(notificationManager);

        /* Starting the Alarm Manager */
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        //An intent to notify the broadcast receiver
        Intent notifyIntent =  new Intent(this, AlarmReceiver.class);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);


//        update = findViewById(R.id.update);
//        update.setOnClickListener(v -> updateNotification());

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> cancelNotification());

        /** To assign the toggle button to the running alarm
         * when the toggle button turns off - when the app
         * is closed
         */
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);

        //Setting up the alarm toggle
        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);
        alarmToggle.setChecked(alarmUp);
        alarmToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String toastMessage = null;
            if(isChecked){
//                long repeatInterval = AlarmManager.INTERVAL_HOUR;
                long repeatInterval = Constants.INTERVAL_FIVE_MINUTES;
                long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;
                if(alarmManager != null) {
                    // starting the alarm
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            triggerTime, repeatInterval, notifyPendingIntent);
                    toastMessage = "Alarm on!";
                }
            } else {
                if(alarmManager != null){
                    alarmManager.cancel(notifyPendingIntent);
                }
                notificationManager.cancelAll();
                toastMessage = "Alarm off!";
            }
            Toast.makeText(HomeActivity.this, toastMessage,Toast.LENGTH_SHORT)
                    .show();
        });

        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));
    }

    private void initRecyclerview() {

    }

    private void sendNotification() {
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID,
                updateIntent,
                PendingIntent.FLAG_ONE_SHOT);


        Intent notificationIntent = new Intent(this, HomeActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                notificationIntent,
                0);

        NotificationCompat.Builder builder = AppUtils.getNotificationBuilder(this,
                "Exam Preparation",
                "This is a preparation of the Google Certification Exam!");
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void updateNotification(){
        Bitmap bitmapImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.mascot_1);
        NotificationCompat.Builder builder = AppUtils.getNotificationBuilder(this,
                "Exam Preparation Update",
                "This is a preparation of the Google Certification Exam!");
        builder.setStyle(new NotificationCompat.BigPictureStyle()
            .bigPicture(bitmapImage)
            .setBigContentTitle("Notification updated!"));
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void cancelNotification(){
        notificationManager.cancel(NOTIFICATION_ID);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    //Broadcast receiver
    public class NotificationReceiver extends BroadcastReceiver{

        public NotificationReceiver(){}

        @Override
        public void onReceive(Context context, Intent intent) {
            updateNotification();
        }
    }
}
