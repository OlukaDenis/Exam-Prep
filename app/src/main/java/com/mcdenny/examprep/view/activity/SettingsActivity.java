package com.mcdenny.examprep.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.view.workers.NotificationWorker;

import java.util.concurrent.TimeUnit;

public class SettingsActivity extends AppCompatActivity {
    public static final String MESSAGE_STATUS = "message_status";
    private WorkManager workManager;
    private TextView tvStatus;
    private Button btnSend;
    private Switch workerSwitch;
    Boolean switch_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        // initializing a work manager
        workManager = WorkManager.getInstance(this);

        //one time work request
        OneTimeWorkRequest mRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class).build();

        //Work request constraints
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(true)
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(false)
                .build();

        Data source = new Data.Builder()
                .putString("workType", "PeriodicTime")
                .build();

        //periodic work request
        PeriodicWorkRequest pRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 15, TimeUnit.MINUTES)
                .setInputData(source)
                .build();


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        workerSwitch = findViewById(R.id.worker_switch);
        workerSwitch.setChecked(preferences.getBoolean("switch_status", false));


        workerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                //passing work request object to the work manager
                workManager.enqueue(pRequest);

                SharedPreferences.Editor trueEditor = preferences.edit();
                trueEditor.putBoolean("switch_status", true);
                trueEditor.apply();
            } else {
                //canceling the work manager
                workManager.cancelWorkById(pRequest.getId());

                SharedPreferences.Editor falseEditor = preferences.edit();
                falseEditor.putBoolean("switch_status", false);
                falseEditor.apply();
            }
        });



        //work info
        workManager.getWorkInfoByIdLiveData(pRequest.getId()).observe(this, workInfo -> {
            if (workInfo != null) {
                WorkInfo.State state = workInfo.getState();
                Toast.makeText(this, state.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
