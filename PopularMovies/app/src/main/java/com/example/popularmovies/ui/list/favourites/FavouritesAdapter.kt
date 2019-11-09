package com.example.popularmovies.ui.list.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.data.model.Movie
import com.example.popularmovies.databinding.ListItemFavouriteBinding


class FavouritesAdapter(private val clickListener: OnClickListener) : RecyclerView.Adapter<FavouritesAdapter.MovieFavouriteViewHolder>(){


    var data: List<Movie>? = listOf<Movie>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavouriteViewHolder {
        return MovieFavouriteViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: MovieFavouriteViewHolder, position: Int) {
        val item   = data?.get(position)
        holder.bind(item, clickListener)
    }


    class MovieFavouriteViewHolder private constructor(private val binding: ListItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: Movie?,
            clickListener: OnClickListener
        ) {
            binding.movie = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovieFavouriteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFavouriteBinding.inflate(layoutInflater, parent, false)

                return MovieFavouriteViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener : (movie : Movie) -> Unit){
        fun onClick(movie : Movie) = clickListener(movie)
    }



}