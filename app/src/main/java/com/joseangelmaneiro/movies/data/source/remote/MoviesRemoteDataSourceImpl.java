package com.joseangelmaneiro.movies.data.source.remote;

import com.joseangelmaneiro.movies.data.exception.NetworkConnectionException;
import com.joseangelmaneiro.movies.data.exception.ServiceException;
import com.joseangelmaneiro.movies.domain.Handler;
import com.joseangelmaneiro.movies.data.entity.MovieEntity;
import com.joseangelmaneiro.movies.data.entity.PageEntity;

import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesRemoteDataSourceImpl implements MoviesRemoteDataSource {

    // TODO Put here your api key (https://developers.themoviedb.org/3/getting-started)
    private static final String API_KEY = "";

    private MovieService movieService;

    @Inject
    public MoviesRemoteDataSourceImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void getAll(final Handler<List<MovieEntity>> handler) {
        movieService.getPageEntity(API_KEY).enqueue(new Callback<PageEntity>() {
            @Override
            public void onResponse(Call<PageEntity> call, Response<PageEntity> response) {
                if(response.isSuccessful()){
                    handler.handle(response.body().movies);
                } else {
                    handler.error(new ServiceException());
                }
            }

            @Override
            public void onFailure(Call<PageEntity> call, Throwable t) {
                handler.error(new NetworkConnectionException());
            }
        });
    }

}
