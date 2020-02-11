package com.mcdenny.examprep.view.workers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.utils.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class BlurWorker extends Worker {
    private static final String TAG = "BlurWorker";

    public BlurWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        Log.d(TAG, "BlurWorker: called...");
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: called....");
        Context applicationContext = getApplicationContext();

        try {
            // Blur the bitmap
            Bitmap output = WorkerUtils.blurBitmap(Constants.selected_movie_bimap, applicationContext);

            // Write bitmap to a temp file
            Uri outputUri = WorkerUtils.writeBitmapToFile(applicationContext, output);

            WorkerUtils.makeStatusNotification("Output is "
                    + outputUri.toString(), applicationContext);

            // If there were no errors, return SUCCESS
            return Result.success();
        } catch (Throwable throwable) {
            Log.e(TAG, "Error applying blur", throwable);
            return Result.failure();
        }
    }



}
