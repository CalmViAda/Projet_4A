package com.example.projet_4a.presentation.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projet_4a.presentation.main.NetworkStatus
import com.example.projet_4a.data.repository.MovieRepository
import com.example.projet_4a.domain.entity.Movie
import io.reactivex.disposables.CompositeDisposable




class SingleMovieViewModel (private val movieRepository : MovieRepository, movieId: Int)  : ViewModel()
{

    private val compositeDisposable = CompositeDisposable()



    val  movieDetails : LiveData<Movie> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkStatus> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }



    override fun onCleared()
    {
        super.onCleared()
        compositeDisposable.dispose()
    }

}