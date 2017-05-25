package com.baya.walmartmovie.ui.moviedetail;


import com.baya.walmartmovie.ui.base.BasePresenter;
import com.baya.walmartmovie.ui.base.BaseView;

public interface MovieDetailContract {

    interface View extends BaseView<MovieDetailContract.Presenter> {

        void showOverview(String overview);
    }

    interface Presenter extends BasePresenter {

        void presentOverview(String overview);
    }
}
