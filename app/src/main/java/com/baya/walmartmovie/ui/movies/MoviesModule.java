package com.baya.walmartmovie.ui.movies;

import dagger.Module;
import dagger.Provides;

@Module
class MoviesModule {

    private MoviesContract.View mView;

    MoviesModule(MoviesContract.View view){
        mView = view;
    }

    @Provides
    MoviesContract.View providesView(){
        return mView;
    }

}
