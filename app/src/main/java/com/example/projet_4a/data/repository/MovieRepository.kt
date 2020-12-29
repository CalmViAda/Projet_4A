package com.example.projet_4a.data.repository

import androidx.lifecycle.LiveData
import com.example.projet_4a.presentation.main.NetworkStatus
import com.example.projet_4a.data.local.MovieApi
import com.example.projet_4a.domain.entity.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieRepository (private val apiService : MovieApi)
{


    lateinit var movieDetailsNetworkDataSource: MovieNetworkDataSource



    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<Movie>
    {

        movieDetailsNetworkDataSource = MovieNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkStatus>
    {
        return movieDetailsNetworkDataSource.networkState
    }

}