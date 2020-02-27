package com.mcdenny.examprep.data.api;

import com.mcdenny.examprep.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.mcdenny.examprep.utils.Constants.API_KEY;

public interface ApiService {
    @GET("trending/movie/week")
    Call<MovieResponse> getTrendingMovies(@Query("api_key") String apiKey,
                                          @Query("page") int page);
}
