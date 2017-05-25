package com.baya.walmartmovie.ui.movies;

import com.baya.walmartmovie.data.DataComponent;
import com.baya.walmartmovie.utils.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = {DataComponent.class}, modules = {MoviesModule.class})
public interface MoviesComponent {

    void inject(MoviesListActivity activity);
}
