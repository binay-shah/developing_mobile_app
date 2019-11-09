package com.example.popularmovies.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.popularmovies.R
import com.example.popularmovies.data.model.Genre
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.model.Trailer
import com.example.popularmovies.ui.details.trailers.TrailersAdapter
import com.example.popularmovies.ui.list.discover.DiscoverMoviesAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: PagedList<MovieProperty>?) {
    val adapter = recyclerView.adapter as DiscoverMoviesAdapter
    adapter.submitList(data)
}


@BindingAdapter("listTrailers")
fun bindTrailersList(recyclerView: RecyclerView, data: List<Trailer>?) {
    val adapter = recyclerView.adapter as TrailersAdapter
    adapter.data = data
}
//@BindingAdapter("networkStatus")
//fun bindStatus(statusImageView: ImageView, networkState: NetworkState?) {
//    when (networkState?.networkState) {
//        Status.LOADING -> {
//            statusImageView.visibility = View.VISIBLE
//            statusImageView.setImageResource(R.drawable.loading_animation)
//        }
//        Status.ERROR -> {
//            statusImageView.visibility = View.VISIBLE
//            statusImageView.setImageResource(R.drawable.ic_connection_error)
//        }
//        Status.DONE -> {
//            statusImageView.visibility = View.GONE
//        }
//    }
//}

@BindingAdapter("imageUrl", "isBackdrop")
fun bindImage(imgView : ImageView, imgPath : String?, isBackdrop: Boolean){
    val  baseUrl =if(isBackdrop) BACKDROP_URL else IMAGE_URL

    imgPath?.let {
        val imgUrl = baseUrl + imgPath
        val imgUri = imgUrl.toUri()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}


@BindingAdapter("imageUrlFav")
fun bindImageFav(imgView : ImageView, imgFav : String?){
    imgFav?.let {


        val imgUrl = IMAGE_URL + it
        val imgUri = imgUrl.toUri()
        Glide.with(imgView.context)
            .load(imgUri)

            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))

            .into(imgView)


    }
}

/**
 * Binding adapter used to hide the spinner once data is available
 */
@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean){
    view.visibility = if(show) View.VISIBLE else View.GONE
}

@BindingAdapter("chipItems")
fun bindChipItems(view: ChipGroup, genres: List<Genre>?){
    if (genres == null
        // Since we are using liveData to observe data, any changes in that table(favorites)
        // will trigger the observer and hence rebinding data, which can lead to duplicates.
        || view.childCount > 0)
        return
    val context = view.context
    for ((_, name) in genres) {
        val chip = Chip(context)
        chip.text = name
        //chip.chipStrokeWidth = UiUtils.dipToPixels(context, 1)
        //chip.chipStrokeColor = ColorStateList.valueOf(
            //context.getResources().getColor(R.color.md_blue_grey_200)
        //)
        //chip.setChipBackgroundColorResource(android.R.color.transparent)
        view.addView(chip)
    }
}

@BindingAdapter("isFavourite")
fun showFavourite(view: FloatingActionButton, isFavourite: Int){
    Log.d("Movie3", ""+isFavourite)
    if(isFavourite == 0)
        view.setImageResource(R.drawable.ic_unfavorite)
    else
        view.setImageResource(R.drawable.ic_favorite_24dp)
}


/**
 * Binding adapter used to display images from URL using Glide
 */
//@BindingAdapter("imageUrl")
//fun setImageUrl(imageView: ImageView, url: String) {
//    Glide.with(imageView.context).load(url).into(imageView)
//}