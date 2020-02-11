package com.mcdenny.examprep.data.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.paging.DataSource;

import com.mcdenny.examprep.data.datasource.MovieDataSource;
import com.mcdenny.examprep.model.Movie;

import java.util.List;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {
    private Context context;
    private MovieDataSource dataSource;

    public MovieDataSourceFactory(Context context) {
        this.context = context;
    }

    @Override
    public DataSource<Integer, Movie> create() {
        if (dataSource == null){
            dataSource = new MovieDataSource(context);
        }
        return dataSource;
    }
}
