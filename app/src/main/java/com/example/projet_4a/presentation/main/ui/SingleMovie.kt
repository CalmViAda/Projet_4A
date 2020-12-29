package com.example.projet_4a.presentation.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.projet_4a.Constants
import com.example.projet_4a.presentation.main.NetworkStatus
import com.example.projet_4a.R
import com.example.projet_4a.data.local.MovieApi
import com.example.projet_4a.data.local.MovieClient
import com.example.projet_4a.data.repository.MovieRepository
import com.example.projet_4a.domain.entity.Movie
import com.example.projet_4a.presentation.main.viewmodels.SingleMovieViewModel
import kotlinx.android.synthetic.main.activity_single_movie.*






private lateinit var viewModel: SingleMovieViewModel
private lateinit var movieRepository: MovieRepository




class SingleMovie : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : MovieApi = MovieClient.getClient()

        movieRepository = MovieRepository(apiService)

        viewModel = getViewModel(movieId)


        viewModel.movieDetails.observe(
            this,
            Observer
            {
                bindUI(it)
            }
        )

        viewModel.networkState.observe(
            this,
            Observer
            {
                progress_bar.visibility =
                    if (it == NetworkStatus.LOADING)
                        View.VISIBLE
                    else
                        View.GONE
                txt_error.visibility =
                    if (it == NetworkStatus.ERROR)
                        View.VISIBLE
                    else
                        View.GONE
            }
        )

    }

    fun bindUI( it: Movie)
    {
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview


        val moviePosterURL = Constants.POSTER_URL + it.posterPath

        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)


    }

    private fun getViewModel(movieId:Int): SingleMovieViewModel
    {
        return ViewModelProvider(this, object : ViewModelProvider.Factory
        {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T
            {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(
                    movieRepository,
                    movieId
                ) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}
