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
    private MovieDao movieDao;
    private AppDatabase database;

    public MovieDataSource(Context context) {
        database = AppDatabase.getDatabase(context);
        movieDao = database.movieDao();
    }

    //This method is called too load initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {
        List<Movie> movies = movieDao.getCouponsBySize(0, params.requestedLoadSize);
        int triesCount = 0;
        while (movies.size() == 0){
            movies = movieDao.getCouponsBySize(0, params.requestedLoadSize);
            triesCount += 1;
            if (triesCount == 6){
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Log.e(TAG, "loadInitial: ",e );
            }
        }
        callback.onResult(movies, null, movies.size() + 1);
    }

    @Override
    public void loadBefore(@NonNull LoadParams params, @NonNull LoadCallback callback) {


    }

    //This method is called to load pages of data using key passed in params
    @Override
    public void loadAfter(@NonNull LoadParams params, @NonNull LoadCallback callback) {
        List<Movie> movies = movieDao.getCouponsBySize((Integer) params.key, params.requestedLoadSize);
        int nextKey = (Integer) params.key + movies.size();
        callback.onResult(movies, nextKey);
    }
}
