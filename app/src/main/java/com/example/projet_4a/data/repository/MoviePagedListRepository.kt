package com.example.projet_4a.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.projet_4a.Constants
import com.example.projet_4a.presentation.main.NetworkStatus
import com.example.projet_4a.data.local.MovieApi
import com.example.projet_4a.domain.entity.MovieItem
import com.example.projet_4a.presentation.main.viewmodels.MovieDataSource
import com.example.projet_4a.presentation.main.viewmodels.MovieDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val apiService : MovieApi)
{


    lateinit var moviePagedList: LiveData<PagedList<MovieItem>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory



    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<MovieItem>>
    {
        moviesDataSourceFactory =
            MovieDataSourceFactory(
                apiService,
                compositeDisposable
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.MOVIE_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }


    fun getNetworkState(): LiveData<NetworkStatus>
    {
        return Transformations.switchMap<MovieDataSource, NetworkStatus>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkStatus)
    }
}