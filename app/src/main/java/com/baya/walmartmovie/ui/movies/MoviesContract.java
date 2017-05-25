package com.baya.walmartmovie.ui.movies;

import com.baya.walmartmovie.data.api.model.Movie;
import com.baya.walmartmovie.ui.base.BasePresenter;
import com.baya.walmartmovie.ui.base.BaseView;

import java.util.List;

public interface MoviesContract {

    interface View extends BaseView<MoviesContract.Presenter> {

        void showMovies(List<Movie> movies);

        void addMoviesOnPagination(List<Movie> movies);

        void showLoadingIndicator(boolean show);

        void showLoadingError();

        void showMovieDetailsUI(String movieId);

        void showPromptSearchDialog();



    }

    interface Presenter extends BasePresenter {

        void loadMovies(String query);

        void loadMovies(String query, int page);

        void openMovieDetails(Movie movie);

        void promptUserToSearch();
    }
}
