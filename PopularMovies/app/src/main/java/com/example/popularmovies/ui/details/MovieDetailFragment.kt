package com.example.popularmovies.ui.details


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularmovies.R
import com.example.popularmovies.data.MovieRepository
import com.example.popularmovies.data.local.MovieDatabase
import com.example.popularmovies.data.local.MovieLocalDataSource
import com.example.popularmovies.data.remote.MovieRemoteDataSource
import com.example.popularmovies.data.remote.network.MovieApi
import com.example.popularmovies.databinding.FragmentMovieDetailBinding


import com.google.android.material.appbar.AppBarLayout

import com.example.popularmovies.data.remote.network.NetworkState
import com.example.popularmovies.data.remote.network.Resource
import com.example.popularmovies.data.remote.network.Status
import com.example.popularmovies.ui.details.trailers.TrailersAdapter
import com.example.popularmovies.utils.YOUTUBE_WEB_URL
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_detail.view.*
import kotlinx.android.synthetic.main.include_movie_detail.view.*


/**
 * A simple [Fragment] subclass.
 *
 */
class MovieDetailFragment : Fragment() {


    lateinit var mBinding : FragmentMovieDetailBinding


    private val viewModel: MovieDetailViewModel by lazy {
        val viewModelFactory = DetailViewModelFactory( MovieRepository.getInstance(
            MovieLocalDataSource.getInstance(MovieDatabase.getDatabase(this.context!!)),
            MovieRemoteDataSource.getInstance(MovieApi.getMovieService)
        ))

          ViewModelProviders.of(this,
            viewModelFactory).get(MovieDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        mBinding = FragmentMovieDetailBinding.inflate(inflater)
        mBinding.lifecycleOwner = this
        val movieId = MovieDetailFragmentArgs.fromBundle(arguments!!).movieId


        mBinding.viewModel = viewModel



        val adapter = TrailersAdapter(TrailersAdapter.VideoClick {
            // When a video is clicked this block or lambda will be called by DevByteAdapter

            // context is not around, we can safely discard this click since the Fragment is no
            // longer on the screen
            val packageManager = context?.packageManager ?: return@VideoClick

            // Try to generate a direct intent to the YouTube app
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + it.key))
            if (intent.resolveActivity(packageManager) == null) {
                // YouTube app isn't found, use the web url

                intent = Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_WEB_URL + it.key))
            }

            startActivity(intent)
        })

       // mBinding.resource = Resource.loading(null)

        mBinding.movieDetailsInfo.trailerList.adapter = adapter

        viewModel.movieDetailsResult.observe(this, Observer {
            mBinding.resource = it
            viewModel.setFavourite(it?.data?.movie?.isFavourite == 1)
            mBinding.movie = it?.let{it.data?.movie}
            mBinding.movieDetailsInfo.movie = it?.let{it.data?.movie}
            mBinding.movieDetailsInfo.movieDetails = it?.let{it.data}

            adapter.data =  it?.data?.trailerList


        })


        viewModel.showSnackBarEvent.observe(this, Observer {
            if (it == true) { // Observed state is true.
                var msg=""
                if(viewModel.isFavourite)
                     msg = getString(R.string.movie_removed_successfully)
                else
                     msg = getString(R.string.movie_added_successfully)
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    msg,
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).apply{
                    view?.setPadding(0,0,0,0)


                }.show()
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
                viewModel.doneShowingSnackbar()
            }
        })


        if(savedInstanceState == null)
            viewModel.setMovieId(movieId)

        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * sets the title on the toolbar only if the toolbar is collapsed
         */

        mBinding.appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener{

            var isShow = true
            var scrollRange  = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout?.totalScrollRange ?: 0
                }
                //verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    mBinding.collapsingToolbar.title = viewModel.movieDetailsResult.value?.data?.movie?.title
                    isShow = true
                } else if (isShow) {
                    //display an empty string when toolbar is expanded
                    mBinding.collapsingToolbar.title = ""
                    isShow = false
                }
            }

        })

        mBinding.collapsingToolbar.setupWithNavController(mBinding.toolbar, findNavController())
    }


}
