package com.mcdenny.examprep.data.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mcdenny.examprep.data.api.ApiClient;
import com.mcdenny.examprep.data.api.ApiService;
import com.mcdenny.examprep.data.db.AppDatabase;
import com.mcdenny.examprep.data.db.MovieDao;
import com.mcdenny.examprep.model.Movie;
import com.mcdenny.examprep.model.MovieResponse;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mcdenny.examprep.utils.Constants.API_KEY;
import static com.mcdenny.examprep.utils.Constants.FIRST_PAGE;

public class MovieRepository {
    private AppDatabase database;
    private MovieDao movieDao;
    private static final String TAG = "MovieRepository";

    public MovieRepository(Application application){
        database = AppDatabase.getDatabase(application);
        movieDao = database.movieDao();
    }

    public LiveData<List<Movie>> getTrendingMovies(){
        refreshMovies();

        return movieDao.getAllMovies(); //getting the saved movies
    }

    //saving to room database
    private void refreshMovies() {
        Log.d(TAG, "refreshMovies: Called....");
        ApiService service = ApiClient.getApiService(ApiService.class);
        Call<MovieResponse> call = service.getTrendingMovies(API_KEY, FIRST_PAGE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    MovieResponse movieResponse = response.body();
                    if (movieResponse != null){
                        List<Movie> movies = movieResponse.getResults();
                        Log.d(TAG, "getTrendingMovies: "+movies.toString());
                        movieDao.insertMovies(movies);
                    }

                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
            }
        });
    }

    public Movie getMovieById(String id){
       return movieDao.getMovie(id);
    }
}
