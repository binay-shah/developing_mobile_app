package com.example.popularmovies.ui.details.trailers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.data.model.Trailer

import com.bumptech.glide.Glide
import com.example.popularmovies.databinding.ListItemTrailerBinding


class TrailersAdapter(val callback: VideoClick) : RecyclerView.Adapter<TrailersAdapter.ViewHolder>(){

    var data: List<Trailer>? = listOf<Trailer>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
         return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item   = data?.get(position)
        holder.bind(item, callback)
    }


    class ViewHolder private constructor(val binding: ListItemTrailerBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Trailer?,  callback:  VideoClick) {
            val thumbnail = "https://img.youtube.com/vi/" + item?.key + "/hqdefault.jpg"
            binding.videoCallback = callback
            binding.trailer = item
            Glide.with(itemView.context)
                .load(thumbnail)
                //.placeholder(com.example.popularmovies.R.color.md_grey_200)
                .into(binding.trailerImage)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemTrailerBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    /**
     * Click listener for Videos. By giving the block a name it helps a reader understand what it does.
     *
     */
    class VideoClick(val block: (Trailer) -> Unit) {
        /**
         * Called when a video is clicked
         *
         * @param video the video that was clicked
         */
        fun onClick(video: Trailer) = block(video)
    }

}

