package com.example.projet_4a.presentation.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.projet_4a.presentation.main.NetworkStatus
import com.example.projet_4a.domain.entity.MovieItem
import com.example.projet_4a.data.repository.MoviePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class MovieActivityViewModel (private val movieRepository : MoviePagedListRepository) : ViewModel()
{

    private val compositeDisposable = CompositeDisposable()



    val  moviePagedList : LiveData<PagedList<MovieItem>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkStatus> by lazy {
        movieRepository.getNetworkState()
    }



    fun listIsEmpty(): Boolean
    {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared()
    {
        super.onCleared()
        compositeDisposable.dispose()
    }

}