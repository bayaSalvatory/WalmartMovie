package com.baya.walmartmovie.ui.movies;


import android.util.Log;

import com.baya.walmartmovie.data.MoviesDatabase;
import com.baya.walmartmovie.data.api.model.Movie;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

import static com.google.common.base.Preconditions.checkNotNull;

class MoviesPresenter implements MoviesContract.Presenter {

    private static final String TAG = MoviesPresenter.class.getSimpleName();
    private final MoviesDatabase moviesDatabase;
    private MoviesContract.View mView;
    private Subscription request;

    @Inject
    MoviesPresenter(MoviesDatabase database, MoviesContract.View view) {
        this.moviesDatabase = checkNotNull(database, "Movies database cannot be null");
        mView = checkNotNull(view, "Movies View cannot be null");
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        promptUserToSearch();
    }

    @Override
    public void onDestroy() {
        if (request != null) request.unsubscribe();
    }

    @Override
    public void loadMovies(String query, final int page) {
        mView.showLoadingIndicator(true);
        request = moviesDatabase.getMovies(query, page, new MoviesDatabase.GetMoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                Log.d(TAG, "Success");
                if (page == 1) {
                    mView.showMovies(movies);
                } else {
                    mView.addMoviesOnPagination(movies);
                }

                mView.showLoadingIndicator(false);
            }

            @Override
            public void onError() {
                Log.d(TAG, "onError");
                mView.showLoadingError();
            }
        });
    }

    @Override
    public void loadMovies(String query) {
        loadMovies(query, 1);
    }

    @Override
    public void openMovieDetails(Movie movie) {
        mView.showMovieDetailsUI(movie.getId());
    }

    @Override
    public void promptUserToSearch() {
        mView.showPromptSearchDialog();
    }
}
