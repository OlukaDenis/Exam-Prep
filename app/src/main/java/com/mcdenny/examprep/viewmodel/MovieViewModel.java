package com.mcdenny.examprep.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.mcdenny.examprep.data.factory.MovieDataSourceFactory;
import com.mcdenny.examprep.data.repository.MovieRepository;
import com.mcdenny.examprep.model.Movie;
import com.mcdenny.examprep.model.User;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private MovieRepository movieRepository;
    public LiveData<PagedList<Movie>> movies;
    private static final String TAG = "MovieViewModel";

    public MovieViewModel(Application application){

        movieRepository = new MovieRepository(application);
//        movies = movieRepository.getTrendingMovies();

        //Instantiating Moviedatasourcefactory
        MovieDataSourceFactory factory = new MovieDataSourceFactory(application);

        //Create a pagedlist config
        PagedList.Config config = (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                                    .setInitialLoadSizeHint(10)
                                    .setPageSize(15).build();
        //create LiveData object using LivePagedListBuilder which takes
        //data source factory and page config as params
        movies = new LivePagedListBuilder<>(factory, config).build();

    }

    public Movie getMovie(String id){
        return movieRepository.getMovieById(id);
    }

    //factory for creating view model,
    // required because we need to pass Application to view model object
    public static class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        private Application mApplication;
        public MovieViewModelFactory(Application application) {
            mApplication = application;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> viewModel) {
            return (T) new MovieViewModel(mApplication);
        }
    }
}
