package com.movies_rxjava.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.movies_rxjava.BuildConfig
import com.movies_rxjava.app.utils.FIRST_PAGE
import com.movies_rxjava.remote.model.Movie
import com.movies_rxjava.source.RemoteDataSource
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class MoviesDataSource(
    private val remoteDataSource: RemoteDataSource,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        compositeDisposable.add(
            remoteDataSource.getPopularMoviesPaging(page)
                .observeOn(Schedulers.io())
                .subscribe(
                    { callback.onResult(it.results, null, page + 1) },
                    { setRetry(Action { loadInitial(params, callback) }) }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        compositeDisposable.add(
            remoteDataSource.getPopularMoviesPaging(params.key)
                .observeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.results, params.key + 1)
                        }
                    },
                    { setRetry(Action { loadAfter(params, callback) }) }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) { }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}