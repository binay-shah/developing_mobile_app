package com.example.popularmovies.ui.list.discover

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.R
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.remote.network.NetworkState
import com.example.popularmovies.data.remote.network.Status
import com.example.popularmovies.databinding.ItemNetworkStateBinding
import com.example.popularmovies.databinding.ListItemMovieBinding


class DiscoverMoviesAdapter(private val clickListener: OnClickListener) : PagedListAdapter<MovieProperty, RecyclerView.ViewHolder>(
    MovieDiffUtilCallBack
) {

    private var networkState: NetworkState? = null

    interface OnClickListener {
        fun onClick(movie : MovieProperty)
        fun onClickRetry()
        fun whenListIsUpdated(size: Int, networkState: NetworkState?)
    }

//    class OnClickListener(val clickListener : (movie : MovieProperty) -> Unit){
//        fun onClick(movie : MovieProperty) = clickListener(movie)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_network_state -> NetworkStateViewHolder.from(
                parent

            )
            R.layout.list_item_movie -> MovieViewHolder.from(
                parent
            )
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.list_item_movie -> (holder as MovieViewHolder).bind(getItem(position), clickListener)
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bind(networkState, clickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (hasExtraRow() && position == itemCount - 1) {
            return R.layout.item_network_state
        } else {
            return R.layout.list_item_movie;
        }
    }

    override fun getItemCount(): Int {
        this.clickListener.whenListIsUpdated(super.getItemCount(), networkState)
        return super.getItemCount()
        //return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

     fun setNetworkState(newNetworkState: NetworkState){

         val previousState = this.networkState
         val hadExtraRow = hasExtraRow()
         this.networkState = newNetworkState
         val hasExtraRow = hasExtraRow()
         if (hadExtraRow != hasExtraRow) {
             if (hadExtraRow) {
                 notifyItemRemoved(super.getItemCount())
             } else {
                 notifyItemInserted(super.getItemCount())
             }
         } else if (hasExtraRow && previousState !== newNetworkState) {
             notifyItemChanged(itemCount -1)
         }
    }


    class MovieViewHolder private constructor(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: MovieProperty?,
            clickListener: OnClickListener
        ) {
            binding.movie = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemMovieBinding.inflate(layoutInflater, parent, false)

                return MovieViewHolder(binding)
            }
        }
    }

    class NetworkStateViewHolder private constructor(private val binding: ItemNetworkStateBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: NetworkState?, clickListener: OnClickListener) {
            binding.networkState = item
            binding.progressBar.visibility = isVisible(item?.status == Status.LOADING)
            binding.retryButton.visibility = isVisible(item?.status == Status.ERROR)
            binding.errorMsg.visibility = isVisible(item?.msg != null)
            binding.errorMsg.text = item?.msg
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): NetworkStateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNetworkStateBinding.inflate(layoutInflater, parent, false)

                return NetworkStateViewHolder(
                    binding

                )
            }
        }

        private fun isVisible(condition: Boolean): Int {
            return if (condition)
                View.VISIBLE
            else
                View.GONE
        }
    }

    companion object  MovieDiffUtilCallBack : DiffUtil.ItemCallback<MovieProperty>() {
        override fun areItemsTheSame(oldItem: MovieProperty, newItem: MovieProperty): Boolean {

            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: MovieProperty, newItem: MovieProperty): Boolean {
            return oldItem == newItem
        }

    }



}


