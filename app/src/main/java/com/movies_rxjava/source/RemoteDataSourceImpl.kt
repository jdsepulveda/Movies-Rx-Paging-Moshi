package com.movies_rxjava.source

import com.movies_rxjava.remote.api.MoviesService
import com.movies_rxjava.remote.model.ResponseWrapper
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val moviesService: MoviesService
) : RemoteDataSource {

    override fun getPopularMovies(): Observable<ResponseWrapper> {
        return moviesService.getPopularMovies()
    }

    override fun getPopularMoviesPaging(page: Int): Observable<ResponseWrapper> {
        return moviesService.getPopularMoviesPaging(page)
    }
}