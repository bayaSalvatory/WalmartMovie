package com.baya.walmartmovie.ui.moviedetail;

import dagger.Module;
import dagger.Provides;

@Module
class MovieDetailModule {

    private MovieDetailContract.View mView;

    MovieDetailModule(MovieDetailContract.View view) {
        mView = view;
    }

    @Provides
    MovieDetailContract.View providesMovieDetailView() {
        return mView;
    }
}
