package com.baya.walmartmovie.ui.moviedetail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.baya.walmartmovie.App;
import com.baya.walmartmovie.R;
import com.baya.walmartmovie.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.baya.walmartmovie.utils.Constants.MOVIE_ID;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {

    @BindView(R.id.over_view)
    TextView mOverViewTextView;

    @Inject
    MovieDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        DaggerMovieDetailComponent.builder()
                .dataComponent(((App) getApplication()).getDataComponent())
                .movieDetailModule(new MovieDetailModule(this))
                .build()
                .inject(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPresenter.presentOverview(bundle.getString(MOVIE_ID));
        }
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        mPresenter = (MovieDetailPresenter) presenter;
    }

    @Override
    public void showOverview(String overview) {
        mOverViewTextView.setText(overview);
    }
}
