package com.movies_rxjava.source

import com.movies_rxjava.remote.model.ResponseWrapper
import io.reactivex.Observable
import io.reactivex.Single

interface RemoteDataSource {

    fun getPopularMovies(): Observable<ResponseWrapper>

    fun getPopularMoviesPaging(page: Int): Observable<ResponseWrapper>
}