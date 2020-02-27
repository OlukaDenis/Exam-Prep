package com.mcdenny.examprep.utils;

import android.graphics.Bitmap;

public final class Constants {

    // Ensures this class is never instantiated
    private Constants() {}

    //Selected movie id
    public static int selected_movie_id;
    public static String selected_movie_poster_url;
    public static Bitmap selected_movie_bimap;


    public static final String API_KEY = "617e3f93f561c0a9a2b934055ba31e6a";
    public static final String IMAGE_URL_BASE_PATH="https://image.tmdb.org/t/p/w342";
    public static final String BACKDROP_URL_BASE_PATH="https://image.tmdb.org/t/p/w500//";
    public static final String YOUTUBE_VIDEO_URL = "https://img.youtube.com/vi/";
    public static final String YOUTUBE_SUFFIX = "/0.jpg";
    public static final String ENGLISH_LANGUAGE = "en-US";
    public static final String WIKIPEDIA_PAGE_URL = "https://en.m.wikipedia.org/wiki/";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    //Notification constants
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    public static final int NOTIFICATION_ID = 0;
    public static final int ALARM_NOTIFICATION_ID = 5;
    public static final String ACTION_UPDATE_NOTIFICATION = "com.mcdenny.examprep.ACTION_UPDATE_NOTIFICATION";


    //Alarm
    public static final long INTERVAL_FIVE_MINUTES = 5 * 60 * 1000;

    // Name of Notification Channel for verbose notifications of background work
    public static final CharSequence VERBOSE_NOTIFICATION_CHANNEL_NAME =
            "Verbose WorkManager Notifications";
    public static String VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts";
    public static final CharSequence NOTIFICATION_TITLE = "WorkRequest Starting";
    public static final String CHANNEL_ID = "VERBOSE_NOTIFICATION" ;

    // The name of the image manipulation work
    public static final String IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work";

    // Other keys
    public static final String OUTPUT_PATH = "blur_filter_outputs";
    public static final String KEY_IMAGE_URI = "KEY_IMAGE_URI";
    public static final String TAG_OUTPUT = "OUTPUT";

    public static final long DELAY_TIME_MILLIS = 3000;

    //Paging
    public static int PAGE_SIZE = 6;
    public static int FIRST_PAGE = 1;

}
