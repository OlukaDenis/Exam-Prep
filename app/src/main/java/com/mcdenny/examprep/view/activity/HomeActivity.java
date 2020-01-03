package com.mcdenny.examprep.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.View;
import android.widget.Button;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.model.Movie;
import com.mcdenny.examprep.utils.Constants;
import com.mcdenny.examprep.view.adapters.MovieAdapter;
import com.mcdenny.examprep.view.fragments.TrendingFragment;
import com.mcdenny.examprep.viewmodel.MovieViewModel;

import java.util.List;

import static com.mcdenny.examprep.utils.Constants.ACTION_UPDATE_NOTIFICATION;
import static com.mcdenny.examprep.utils.Constants.NOTIFICATION_ID;
import static com.mcdenny.examprep.utils.Constants.PRIMARY_CHANNEL_ID;

public class HomeActivity extends AppCompatActivity {
    private Button notify, update, cancel;
    private MovieViewModel viewModel;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    private NotificationManager notificationManager;
    private NotificationReceiver mReceiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                initRecyclerview();
                adapter.notifyDataSetChanged();
            }
        });

        notify = findViewById(R.id.notify);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });


        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotification();
            }
        });
        createNotificationChannel();

        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));
    }

    private void initRecyclerview() {
        recyclerView = findViewById(R.id.trending_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MovieAdapter(this, viewModel.getMovies().getValue());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void sendNotification() {
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID,
                updateIntent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = getNotificationBuilder();
        notificationBuilder.addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void createNotificationChannel(){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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

    private NotificationCompat.Builder getNotificationBuilder(){
        Intent notificationIntent = new Intent(this, MovieDetailActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                notificationIntent,
                0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentTitle("Exam Preparation")
                .setContentText("This is a preparation of the Google Certification Exam!")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        return builder;
    }

    private void updateNotification(){
        Bitmap bitmapImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.mascot_1);
        NotificationCompat.Builder builder = getNotificationBuilder();
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
