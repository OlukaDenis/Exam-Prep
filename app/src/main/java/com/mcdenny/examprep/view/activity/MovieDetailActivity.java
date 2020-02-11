package com.mcdenny.examprep.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Data;
import androidx.work.WorkInfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.mcdenny.examprep.R;
import com.mcdenny.examprep.model.Movie;
import com.mcdenny.examprep.utils.Constants;
import com.mcdenny.examprep.viewmodel.BlurViewModel;
import com.mcdenny.examprep.viewmodel.MovieViewModel;
import com.squareup.picasso.Picasso;

import static com.mcdenny.examprep.utils.Constants.IMAGE_URL_BASE_PATH;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView movie_poster;
    private MovieViewModel movieViewModel;
    private String movieId;
    private static final String TAG = "MovieDetailActivity";
    private BlurViewModel blurViewModel;
    private ProgressBar mProgressBar;
    private Button mGoButton, mOutputButton, mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        Movie movie = movieViewModel.getMovie(movieId);

        blurViewModel = new ViewModelProvider(this).get(BlurViewModel.class);

        //get the movie id
        movieId = String.valueOf(Constants.selected_movie_id);
        Log.d(TAG, "Movie Id: "+ Constants.selected_movie_id);
        Log.d(TAG, "onCreate: The saved bitmap: "+Constants.selected_movie_bimap);

        // Get all of the Views
        mProgressBar = findViewById(R.id.progress_bar);
        mGoButton = findViewById(R.id.go_button);
        mOutputButton = findViewById(R.id.see_file_button);
        mCancelButton = findViewById(R.id.cancel_button);
        movie_poster = findViewById(R.id.movie_poster);

        // Image uri should be stored in the ViewModel; put it there then display
        Intent intent = getIntent();
        String imageUriExtra = intent.getStringExtra(Constants.KEY_IMAGE_URI);
        blurViewModel.setImageUri(imageUriExtra);
        Log.d(TAG, "onCreate -- Image URI: "+blurViewModel.getImageUri());
        if (blurViewModel.getImageUri() != null) {
            Picasso.get()
                    .load(blurViewModel.getImageUri())
                    .placeholder(R.drawable.ic_movie)
                    .error(R.drawable.ic_movie)
                    .into(movie_poster);
        }

        // Setup blur image file button
        mGoButton.setOnClickListener(view -> blurViewModel.applyBlur(getBlurLevel()));

        // Setup view output image file button
        mOutputButton.setOnClickListener(view -> {
            Uri currentUri = blurViewModel.getOutputUri();
            if (currentUri != null) {
                Intent actionView = new Intent(Intent.ACTION_VIEW, currentUri);
                if (actionView.resolveActivity(getPackageManager()) != null) {
                    startActivity(actionView);
                }
            }
        });

        // Hookup the Cancel button
        mCancelButton.setOnClickListener(view -> blurViewModel.cancelWork());

        // Show work status
        blurViewModel.getOutputWorkInfo().observe(this, listOfWorkInfo -> {

            // Note that these next few lines grab a single WorkInfo if it exists
            // This code could be in a Transformation in the ViewModel; they are included here
            // so that the entire process of displaying a WorkInfo is in one location.

            // If there are no matching work info, do nothing
            if (listOfWorkInfo == null || listOfWorkInfo.isEmpty()) {
                return;
            }

            // We only care about the one output status.
            // Every continuation has only one worker tagged TAG_OUTPUT
            WorkInfo workInfo = listOfWorkInfo.get(0);

            boolean finished = workInfo.getState().isFinished();
            if (!finished) {
                showWorkInProgress();
            } else {
                showWorkFinished();

                // Normally this processing, which is not directly related to drawing views on
                // screen would be in the ViewModel. For simplicity we are keeping it here.
                Data outputData = workInfo.getOutputData();

                String outputImageUri =
                        outputData.getString(Constants.KEY_IMAGE_URI);

                // If there is an output file show "See File" button
                if (!TextUtils.isEmpty(outputImageUri)) {
                    blurViewModel.setOutputUri(outputImageUri);
                    mOutputButton.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    /**
     * Shows and hides views for when the Activity is processing an image
     */
    private void showWorkInProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mCancelButton.setVisibility(View.VISIBLE);
        mGoButton.setVisibility(View.GONE);
        mOutputButton.setVisibility(View.GONE);
    }

    /**
     * Shows and hides views for when the Activity is done processing an image
     */
    private void showWorkFinished() {
        mProgressBar.setVisibility(View.GONE);
        mCancelButton.setVisibility(View.GONE);
        mGoButton.setVisibility(View.VISIBLE);
    }

    /**
     * Get the blur level from the radio button as an integer
     * @return Integer representing the amount of times to blur the image
     */
    private int getBlurLevel() {
        RadioGroup radioGroup = findViewById(R.id.radio_blur_group);

        switch(radioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_blur_lv_1:
                return 1;
            case R.id.radio_blur_lv_2:
                return 2;
            case R.id.radio_blur_lv_3:
                return 3;
        }

        return 1;
    }

    private void populateDetails() {
        Movie movie = movieViewModel.getMovie(movieId);

        String image_url = IMAGE_URL_BASE_PATH + movie.getPosterPath();
        Log.d(TAG, "Image poster Url: "+image_url);
        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.ic_movie)
                .error(R.drawable.ic_movie)
                .into(movie_poster);
    }
}
