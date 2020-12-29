package com.example.projet_4a.presentation.main.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projet_4a.Constants
import com.example.projet_4a.presentation.main.NetworkStatus
import com.example.projet_4a.R
import com.example.projet_4a.domain.entity.MovieItem
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*


class PopularMoviePagedListAdapter(public val context: Context) : PagedListAdapter<MovieItem, RecyclerView.ViewHolder>(
    MovieDiffCallback()
)
{

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkStatus? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE)
        {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return MovieItemViewHolder(
                view
            )
        }
        else
        {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(
                view
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE)
        {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
        else
        {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean
    {
        return networkState != null && networkState != NetworkStatus.LOADED
    }


    override fun getItemCount(): Int
    {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }


    override fun getItemViewType(position: Int): Int
    {
        return if (hasExtraRow() && position == itemCount - 1)
        {
            NETWORK_VIEW_TYPE
        }
        else
        {
            MOVIE_VIEW_TYPE
        }
    }



    class MovieDiffCallback : DiffUtil.ItemCallback<MovieItem>()
    {
        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean
        {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean
        {
            return oldItem == newItem
        }

    }


    class MovieItemViewHolder (view: View) : RecyclerView.ViewHolder(view)
    {

        fun bind(movie: MovieItem?,context: Context)
        {
            itemView.cv_movie_title.text = movie?.title
            itemView.cv_movie_release_date.text =  movie?.releaseDate

            val moviePosterURL:String = Constants.POSTER_URL + movie?.posterPath

            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.cv_iv_movie_poster);

            itemView.setOnClickListener{
                val intent = Intent(context, SingleMovie::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }

        }

    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view)
    {

        fun bind(networkState: NetworkStatus?)
        {
            if (networkState != null && networkState == NetworkStatus.LOADING)
            {
                itemView.progress_bar_item.visibility = View.VISIBLE;
            }
            else
            {
                itemView.progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkStatus.ERROR)
            {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.message;
            }
            else if (networkState != null && networkState == NetworkStatus.ENDOFLIST)
            {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.message;
            }
            else
            {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }
    }



    fun setNetworkState(newNetworkState: NetworkStatus)
    {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow)
        {
            if (hadExtraRow)
            {
                notifyItemRemoved(super.getItemCount())
            }
            else
            {
                notifyItemInserted(super.getItemCount())
            }
        }
        else if (hasExtraRow && previousState != newNetworkState)
        {
            notifyItemChanged(itemCount - 1)
        }

    }

}