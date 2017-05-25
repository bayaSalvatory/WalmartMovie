package com.baya.walmartmovie.ui.moviedetail;

import com.baya.walmartmovie.data.DataComponent;
import com.baya.walmartmovie.utils.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = {DataComponent.class}, modules = {MovieDetailModule.class})
public interface MovieDetailComponent {
    void inject(MovieDetailActivity activity);
}
