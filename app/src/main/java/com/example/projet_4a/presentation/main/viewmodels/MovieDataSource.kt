package com.example.projet_4a.presentation.main.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.projet_4a.domain.entity.MovieItem
import androidx.paging.PageKeyedDataSource
import com.example.projet_4a.Constants
import com.example.projet_4a.presentation.main.NetworkStatus
import com.example.projet_4a.data.local.MovieApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource (private val apiService : MovieApi,
                       private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MovieItem>()
{



    private var page = Constants.FIRST_Page
    val networkStatus: MutableLiveData<NetworkStatus> = MutableLiveData()





    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieItem>
    )
    {
        networkStatus.postValue(NetworkStatus.LOADING)
        compositeDisposable
            .add(
                apiService.getPopularMovie(page)
                    .subscribeOn(Schedulers.io())
                    .subscribe
                        (
                            {
                                callback.onResult(it.movieList, null, page+1)
                                networkStatus.postValue(NetworkStatus.LOADED)
                            },
                            {
                                networkStatus.postValue(NetworkStatus.ERROR)
                                Log.e("MovieDataSource", it.message.toString())
                            }
                        )
            )
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieItem>)
    {
        networkStatus.postValue(NetworkStatus.LOADING)

        compositeDisposable
            .add(
                apiService.getPopularMovie(params.key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            if(it.totalPages >= params.key)
                            {
                                callback.onResult(it.movieList, params.key+1)
                                networkStatus.postValue(NetworkStatus.LOADED)

                            }else
                            {
                                networkStatus.postValue(NetworkStatus.ENDOFLIST)

                            }
                        },
                        {
                            networkStatus.postValue(NetworkStatus.ERROR)
                            Log.e("MovieDataSource", it.message.toString())
                        }
                    )
            )
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieItem>) {}

}