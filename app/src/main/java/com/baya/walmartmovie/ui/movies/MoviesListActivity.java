package com.baya.walmartmovie.ui.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baya.walmartmovie.App;
import com.baya.walmartmovie.R;
import com.baya.walmartmovie.data.api.model.Movie;
import com.baya.walmartmovie.ui.base.BaseActivity;
import com.baya.walmartmovie.ui.moviedetail.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.baya.walmartmovie.utils.Constants.MOVIE_ID;

public class MoviesListActivity extends BaseActivity implements MoviesContract.View {

    @Inject
    MoviesPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MoviesListAdapter mAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerMoviesComponent.builder()
                .dataComponent(((App) getApplication()).getDataComponent())
                .moviesModule(new MoviesModule(this))
                .build()
                .inject(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                System.out.println("Page " + page);
                mPresenter.loadMovies(mQuery, page);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);

        mAdapter = new MoviesListAdapter(new ArrayList<Movie>(0), movieItemListener);
        mRecyclerView.setAdapter(mAdapter);


        mPresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;
                mPresenter.loadMovies(mQuery);
                return false;
            }
        });
        return true;
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = (MoviesPresenter) presenter;
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mAdapter.replaceData(movies);
    }

    @Override
    public void addMoviesOnPagination(List<Movie> movies) {
        mAdapter.addData(movies);
    }

    @Override
    public void showLoadingIndicator(boolean show) {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showMovieDetailsUI(String movieId) {
        Intent intent = new Intent(MoviesListActivity.this, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void showPromptSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.prompt_search_msg));
        builder.setNegativeButton(getString(R.string.done), null);
        builder.show();
    }

    MovieItemListener movieItemListener = new MovieItemListener() {
        @Override
        public void onMovieClick(Movie movie) {
            mPresenter.openMovieDetails(movie);
        }
    };

    class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

        private List<Movie> movieList = new ArrayList<>();
        private MovieItemListener movieItemListener;

        MoviesListAdapter(List<Movie> movieList, MovieItemListener listener) {
            this.movieList.addAll(movieList);
            this.movieItemListener = listener;
        }

        void replaceData(List<Movie> movieList) {
            this.movieList.clear();
            this.movieList.addAll(movieList);
            notifyDataSetChanged();
        }

        void addData(List<Movie> movieList) {
            this.movieList.addAll(movieList);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.movie_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Movie movie = movieList.get(position);
            if (movie != null) {
                holder.title.setText(movie.getTitle());
                holder.popularity.setText(movie.getPopularity());
                holder.releaseDate.setText(movie.getReleaseDate());
                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movieItemListener.onMovieClick(movie);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout container;
            TextView title;
            TextView releaseDate;
            TextView popularity;

            ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                releaseDate = (TextView) itemView.findViewById(R.id.release_date);
                popularity = (TextView) itemView.findViewById(R.id.popularity);
                container = (LinearLayout) itemView.findViewById(R.id.container);
            }
        }
    }

    interface MovieItemListener {
        void onMovieClick(Movie movie);
    }
}
