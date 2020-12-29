package com.example.projet_4a.presentation.main.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.projet_4a.data.local.MovieApi
import com.example.projet_4a.domain.entity.MovieItem
import io.reactivex.disposables.CompositeDisposable
import androidx.paging.DataSource
import com.example.projet_4a.presentation.main.viewmodels.MovieDataSource

class MovieDataSourceFactory(private val apiService : MovieApi,
                             private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieItem>()
{


    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()




    override fun create(): DataSource<Int, MovieItem>
    {
        val movieDataSource =
            MovieDataSource(
                apiService,
                compositeDisposable
            )

        moviesLiveDataSource.postValue(movieDataSource)

        return movieDataSource
    }

}