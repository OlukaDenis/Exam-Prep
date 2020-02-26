package com.mcdenny.examprep.data.datasource;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.mcdenny.examprep.data.api.ApiClient;
import com.mcdenny.examprep.data.api.ApiService;
import com.mcdenny.examprep.data.db.AppDatabase;
import com.mcdenny.examprep.data.db.MovieDao;
import com.mcdenny.examprep.data.repository.MovieRepository;
import com.mcdenny.examprep.model.Movie;
import com.mcdenny.examprep.model.MovieResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mcdenny.examprep.utils.Constants.API_KEY;
import static com.mcdenny.examprep.utils.Constants.FIRST_PAGE;

//Data source for PagedList, it is used for loading data for each page
public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {
    private static final String TAG = "MovieDataSource";


    //This method is called too load initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<MovieResponse> call = service.getTrendingMovies(API_KEY, FIRST_PAGE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if(movieResponse != null){
                    List<Movie> responseResults = movieResponse.getResults();
                    callback.onResult(responseResults, null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<MovieResponse> call = service.getTrendingMovies(API_KEY, (Integer) params.key);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if(movieResponse != null){
                    List<Movie> responseResults = movieResponse.getResults();
                    int key;
                    if ((Integer) params.key > 1){
                        key = (Integer) params.key - 1;
                    } else {
                        key = 0;
                    }
                    callback.onResult(responseResults, key);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

    }

    //This method is called to load pages of data using key passed in params
    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<MovieResponse> call = service.getTrendingMovies(API_KEY, (Integer) params.key);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if(movieResponse != null){
                    List<Movie> responseResults = movieResponse.getResults();
                    callback.onResult(responseResults, (Integer) params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
