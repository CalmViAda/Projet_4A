package com.example.projet_4a.presentation.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projet_4a.presentation.main.NetworkStatus
import com.example.projet_4a.R
import com.example.projet_4a.data.local.MovieApi
import com.example.projet_4a.data.local.MovieClient
import com.example.projet_4a.presentation.main.viewmodels.MovieActivityViewModel
import com.example.projet_4a.data.repository.MoviePagedListRepository
import kotlinx.android.synthetic.main.activity_movie.*


class MovieActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieActivityViewModel

    lateinit var movieRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val apiService : MovieApi = MovieClient.getClient()

        movieRepository =
            MoviePagedListRepository(
                apiService
            )

        viewModel = getViewModel()

        val movieAdapter =
            PopularMoviePagedListAdapter(
                this
            )

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup()
        {
            override fun getSpanSize(position: Int): Int
            {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        }


        moviesRecyclerView.layoutManager = gridLayoutManager
        moviesRecyclerView.setHasFixedSize(true)
        moviesRecyclerView.adapter = movieAdapter

        viewModel.moviePagedList.observe(
            this,
            Observer
            {
                movieAdapter.submitList(it)
            }
        )

        viewModel.networkState.observe(
            this,
            Observer
            {
                progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkStatus.LOADING) View.VISIBLE else View.GONE
                txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkStatus.ERROR) View.VISIBLE else View.GONE

                if (!viewModel.listIsEmpty())
                {
                    movieAdapter.setNetworkState(it)
                }
            }
        )

    }


    private fun getViewModel(): MovieActivityViewModel
    {
        return ViewModelProvider(this, object : ViewModelProvider.Factory
        {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T
            {
                @Suppress("UNCHECKED_CAST")
                return MovieActivityViewModel(
                    movieRepository
                ) as T
            }
        })[MovieActivityViewModel::class.java]
    }
}
