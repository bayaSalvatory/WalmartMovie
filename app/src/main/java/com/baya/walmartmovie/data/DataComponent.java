package com.baya.walmartmovie.data;

import com.baya.walmartmovie.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class, ApplicationModule.class})
public interface DataComponent {

    MoviesDatabase getMoviesDatabase();
}
