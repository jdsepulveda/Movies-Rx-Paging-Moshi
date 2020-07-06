package com.movies_rxjava.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.movies_rxjava.paging.MoviePagedListRepository
import com.movies_rxjava.remote.model.Movie
import com.movies_rxjava.source.RemoteDataSource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FragmentPopularMoviesVM @Inject constructor(
    remoteDataSource: RemoteDataSource
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val movieRepository = MoviePagedListRepository(remoteDataSource)

    val popularMoviesList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}