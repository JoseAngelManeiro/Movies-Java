package com.joseangelmaneiro.movies.ui.list;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.joseangelmaneiro.movies.R;
import com.joseangelmaneiro.movies.di.Injection;
import com.joseangelmaneiro.movies.ui.BaseActivity;
import com.joseangelmaneiro.movies.ui.Formatter;
import com.joseangelmaneiro.movies.ui.detail.DetailMovieActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListActivity extends BaseActivity implements MovieListView {

    private MovieListPresenter presenter;

    private MoviesAdapter adapter;

    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        bindViews();

        setUpPresenter();

        setUpActionBar();

        setUpListView();

        setUpRefreshView();

        informPresenterViewIsReady();

    }

    private void bindViews(){
        ButterKnife.bind(this);
    }

    private void setUpPresenter(){
        presenter = new MovieListPresenter(
                Injection.provideRepository(getApplicationContext()),
                new Formatter());
        presenter.setView(this);
    }

    private void setUpActionBar(){
        setSupportActionBar(toolbar);
    }

    private void setUpListView(){
        adapter = new MoviesAdapter(presenter);
        recyclerView.setAdapter(adapter);
    }

    private void setUpRefreshView(){
        refreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }

    private void informPresenterViewIsReady(){
        presenter.viewReady();
    }

    @Override
    public void refreshList() {
        adapter.refreshData();
    }

    @Override
    public void cancelRefreshDialog() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void navigateToDetailScreen(int movieId) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

}