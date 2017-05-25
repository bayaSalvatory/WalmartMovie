package com.baya.walmartmovie;

import android.app.Application;

import com.baya.walmartmovie.data.DataModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DataModule.class})
public class ApplicationModule {

    private App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return this.app;
    }
}
