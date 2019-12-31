package com.mcdenny.examprep.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mcdenny.examprep.data.repository.MovieRepository;
import com.mcdenny.examprep.model.Movie;
import com.mcdenny.examprep.model.User;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<Movie>> movies;

    public MovieViewModel(Application application){
        super(application);
        movieRepository = new MovieRepository(application);
        movies = movieRepository.getTrendingMovies();
    }

    public LiveData<List<Movie>> getMovies(){
        return movies;
    }
}
