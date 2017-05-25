package com.baya.walmartmovie.data.api;


import com.baya.walmartmovie.data.api.model.MovieResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface MoviesServices {
    @GET("3/search/movie")
    Observable<MovieResponse> getMovie(@Query("api_key") String apiKey,
                                       @Query("language") String language,
                                       @Query("query") String query,
                                       @Query("page") int page,
                                       @Query("include_adult") boolean adult);
}
