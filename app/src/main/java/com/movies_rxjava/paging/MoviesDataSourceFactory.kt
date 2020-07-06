package com.movies_rxjava.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.movies_rxjava.remote.model.Movie
import com.movies_rxjava.source.RemoteDataSource
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSourceFactory(
    private val remoteDataSource: RemoteDataSource,
    private val compositeDisposable: CompositeDisposable
): DataSource.Factory<Int, Movie>() {

    private val moviesDataSourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource = MoviesDataSource(remoteDataSource, compositeDisposable)

        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource
    }
}
