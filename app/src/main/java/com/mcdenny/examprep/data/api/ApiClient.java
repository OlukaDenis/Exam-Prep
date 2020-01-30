package com.mcdenny.examprep.data.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mcdenny.examprep.utils.Constants.BASE_URL;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static final int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;

    public static ApiService getApiService(){
        return getClient().create(ApiService.class);
    }


    private static Retrofit getClient(){
        if (okHttpClient == null)
            initOkHttp();
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp(){
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient = httpClient.build();
    }
}
