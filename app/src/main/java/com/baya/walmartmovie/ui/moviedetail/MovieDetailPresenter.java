package com.baya.walmartmovie.ui.moviedetail;

import com.baya.walmartmovie.data.MoviesDatabase;
import com.baya.walmartmovie.data.api.model.Movie;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MoviesDatabase mMoviesDatabase;

    private MovieDetailContract.View mView;

    @Inject
    MovieDetailPresenter(MoviesDatabase database, MovieDetailContract.View view) {
        mMoviesDatabase = checkNotNull(database, "Movie database is null");
        mView = checkNotNull(view, "Movie Detail view cannot be null");
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void presentOverview(String movieId) {
        Movie movie = mMoviesDatabase.getMovieById(movieId);
        if (movie != null) {
            mView.showOverview(movie.getOverview());
        }
    }
}
