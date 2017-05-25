package com.baya.walmartmovie;


import android.app.Application;

import com.baya.walmartmovie.data.DaggerDataComponent;
import com.baya.walmartmovie.data.DataComponent;

public class App extends Application {

    private DataComponent mDataComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mDataComponent = DaggerDataComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public DataComponent getDataComponent() {
        return mDataComponent;
    }

}
