package com.mcdenny.examprep.data.repository;

import android.app.Application;
import android.content.Context;

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

public class MovieRepository {
    private ApiService apiService;
    private AppDatabase database;
    private MovieDao movieDao;
    private static MovieRepository instance;

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
        apiService = ApiClient.getApiService();
        apiService.getTrendingMovies(API_KEY,1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    MovieResponse movieResponse = response.body();
                    List<Movie> movies = movieResponse.getResults();
                    movieDao.insertMovies(movies);
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
