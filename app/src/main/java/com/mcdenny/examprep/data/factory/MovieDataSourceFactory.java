package com.mcdenny.examprep.data.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.mcdenny.examprep.data.datasource.MovieDataSource;
import com.mcdenny.examprep.model.Movie;

import java.util.List;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {
    private Context context;
    private MovieDataSource dataSource;


    public MutableLiveData<MovieDataSource> movieDataSourceMutableLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Movie> create() {
       dataSource = new MovieDataSource();
       movieDataSourceMutableLiveData.postValue(dataSource);
       return dataSource;
    }
}
