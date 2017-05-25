package com.baya.walmartmovie.data;


import android.support.annotation.Nullable;

import com.baya.walmartmovie.data.api.MoviesServices;
import com.baya.walmartmovie.data.api.model.Movie;
import com.baya.walmartmovie.data.api.model.MovieResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.baya.walmartmovie.utils.Constants.API_KEY;
import static com.baya.walmartmovie.utils.Constants.LANGUAGE;
import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class MoviesDatabase {

    private Map<String, Movie> mCacheMovies = new LinkedHashMap<>();
    private MoviesServices services;

    @Inject
    MoviesDatabase(MoviesServices services) {
        this.services = services;
    }

    public Subscription getMovies(String query, int page, final GetMoviesCallback callback) {
        return services.getMovie(API_KEY, LANGUAGE, query, page, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends MovieResponse>>() {
                    @Override
                    public Observable<? extends MovieResponse> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribe(new Subscriber<MovieResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError();
                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        refreshCache(movieResponse.getMovieList());
                        callback.onSuccess(new ArrayList<>(mCacheMovies.values()));

                    }
                });

    }

    @Nullable
    public Movie getMovieById(String movieId) {
        checkNotNull(movieId);
        if (mCacheMovies == null || mCacheMovies.isEmpty()) {
            return null;
        } else {
            return mCacheMovies.get(movieId);
        }
    }

    private void refreshCache(List<Movie> movieList) {
        if (mCacheMovies == null) {
            mCacheMovies = new LinkedHashMap<>();
        }

        mCacheMovies.clear();
        for (Movie movie : movieList) {
            mCacheMovies.put(movie.getId(), movie);
        }
    }

    public interface GetMoviesCallback {

        void onSuccess(List<Movie> movies);

        void onError();
    }
}
