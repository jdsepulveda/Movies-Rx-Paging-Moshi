package com.movies_rxjava.remote.api

import com.movies_rxjava.remote.model.ResponseWrapper
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getPopularMovies(): Observable<ResponseWrapper>

    @GET("movie/popular")
    fun getPopularMoviesPaging(@Query("page") page: Int): Observable<ResponseWrapper>
}