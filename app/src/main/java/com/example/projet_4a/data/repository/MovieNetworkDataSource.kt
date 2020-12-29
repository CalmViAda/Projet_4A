package com.example.projet_4a.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projet_4a.presentation.main.NetworkStatus
import com.example.projet_4a.data.local.MovieApi
import com.example.projet_4a.domain.entity.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieNetworkDataSource (private val apiService : MovieApi, private val compositeDisposable: CompositeDisposable)
{

    private val _networkState  = MutableLiveData<NetworkStatus>()

    val networkState: LiveData<NetworkStatus>
        get() = _networkState

    private val _downloadedMovieDetailsResponse =  MutableLiveData<Movie>()

    val downloadedMovieResponse: LiveData<Movie>

        get() = _downloadedMovieDetailsResponse




    fun fetchMovieDetails(movieId: Int)
    {
        _networkState.postValue(NetworkStatus.LOADING)

        try
        {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkStatus.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkStatus.ERROR)
                            Log.e("MovieDetailsDataSource", it.message.toString())
                        }
                    )
            )
        }
        catch (e: Exception)
        {
            Log.e("MovieDetailsDataSource",e.message.toString())
        }
    }
}