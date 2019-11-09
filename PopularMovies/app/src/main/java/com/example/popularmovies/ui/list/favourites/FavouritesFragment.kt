package com.example.popularmovies.ui.list.favourites


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.popularmovies.data.MovieRepository
import com.example.popularmovies.data.local.MovieDatabase
import com.example.popularmovies.data.local.MovieLocalDataSource
import com.example.popularmovies.data.remote.MovieRemoteDataSource
import com.example.popularmovies.data.remote.network.MovieApi
import com.example.popularmovies.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment() {

    lateinit var binding: FragmentFavouritesBinding

    private val viewModel: FavouritesViewModel by lazy {

        val viewModelFactory = FavouritesViewModelFactory(
            MovieRepository.getInstance(
                MovieLocalDataSource.getInstance(MovieDatabase.getDatabase(this.context!!)),
                MovieRemoteDataSource.getInstance(MovieApi.getMovieService)
            )
        )

        ViewModelProviders.of(this, viewModelFactory
        ).get(FavouritesViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding  = FragmentFavouritesBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel



        val adapter = FavouritesAdapter(FavouritesAdapter.OnClickListener{
            viewModel.displayMovieDetails(it)
        })

        binding.favouriteList.adapter = adapter

        viewModel.favouriteList.observe(this, Observer {
            adapter.data = it
        })

        viewModel.navigateToSelectedMovie.observe(this, Observer {
            it?.let {
                this.findNavController().navigate(
                    FavouritesFragmentDirections.actionShowDetail(
                        it.id
                    )
                )
                viewModel.displayMovieDetailsComplete()
            }
        })

        return binding.root

    }


}
