package com.movies_rxjava.paging

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.movies_rxjava.app.utils.POST_PER_PAGE
import com.movies_rxjava.app.utils.Resource
import com.movies_rxjava.remote.model.Movie
import com.movies_rxjava.source.RemoteDataSource
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val remoteDataSource: RemoteDataSource) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MoviesDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MoviesDataSourceFactory(remoteDataSource, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .setInitialLoadSizeHint(POST_PER_PAGE * 2)
            .setPrefetchDistance(1)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

//    fun getNetworkState(): LiveData<NetworkState> {
//        return Transformations.switchMap<MovieDataSource, NetworkState>(
//            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
//    }
}