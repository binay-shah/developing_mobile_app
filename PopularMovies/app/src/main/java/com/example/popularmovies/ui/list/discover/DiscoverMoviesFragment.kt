package com.example.popularmovies.ui.list.discover


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.example.popularmovies.MainActivity
import com.example.popularmovies.R

import com.example.popularmovies.data.MovieRepository
import com.example.popularmovies.data.local.MovieDatabase
import com.example.popularmovies.data.local.MovieLocalDataSource
import com.example.popularmovies.data.model.MovieProperty
import com.example.popularmovies.data.remote.MovieRemoteDataSource
import com.example.popularmovies.data.remote.network.MovieApi
import com.example.popularmovies.data.remote.network.MovieApiFilter
import com.example.popularmovies.data.remote.network.NetworkState
import com.example.popularmovies.data.remote.network.Status
import com.example.popularmovies.databinding.FragmentDiscoverMoviesBinding
import com.example.popularmovies.storage.SharedPrefsManager
import com.example.popularmovies.utils.ItemOffsetDecoration
import com.example.popularmovies.utils.convertFilterToIndex
import com.example.popularmovies.utils.convertIndexToFilter
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 *
 */
class DiscoverMoviesFragment : Fragment(), DiscoverMoviesAdapter.OnClickListener {


    override fun onClick(movie: MovieProperty) {
        viewModel.displayMovieDetails(movie)
    }

    override fun onClickRetry() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun whenListIsUpdated(size: Int, networkState: NetworkState?) {
        Timber.d("whenListIsUpdated: "+ "size: " +  size + " NetworkState: "+ networkState)
        updateUIWhenLoading(size, networkState)
        updateUIWhenEmptyList(size, networkState)
    }

    private fun updateUIWhenEmptyList(size: Int, networkState: NetworkState?) {
        binding.emptyListImage.visibility = View.GONE
        binding.emptyListButton.visibility = View.GONE
        binding.emptyListTitle.visibility = View.GONE

        if (size == 0) {
            when (networkState?.status) {
                Status.SUCCESS -> {

                    binding.emptyListTitle.text = getString(R.string.no_result_found)
                    binding.emptyListImage.visibility = View.VISIBLE
                    binding.emptyListTitle.visibility = View.VISIBLE
                    binding.emptyListButton.visibility = View.GONE
                }
                Status.ERROR-> {
                    Timber.d("updateUIWhenEmptyList: "+ "error")

                    binding.emptyListTitle.text = getString(R.string.technical_error)
                    binding.emptyListImage.visibility = View.VISIBLE
                    binding.emptyListTitle.visibility = View.VISIBLE
                    binding.emptyListButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateUIWhenLoading(size: Int, networkState: NetworkState?) {

       // Timber.d(""+networkState + "size: "+size)
        //binding.movieList.visibility = if (size == 0 && networkState?.status == Status.LOADING) View.GONE else View.VISIBLE

        binding.progressBar.visibility = if (size == 0 && networkState?.status == Status.LOADING) View.VISIBLE else View.GONE
        //Timber.d("binding.progressBar.visibility :"+ if (size == 0 && networkState?.status == Status.LOADING) "View.VISIBLE" else "View.GONE" )




    }

    private val viewModel: DiscoverMoviesViewModel by lazy {

        val viewModelFactory = DiscoverMoviesViewModelFactory(
            MovieRepository.getInstance(
                MovieLocalDataSource.getInstance(MovieDatabase.getDatabase(this.context!!)),
                MovieRemoteDataSource.getInstance(MovieApi.getMovieService)
            ),
            SharedPrefsManager(this.context!!)
        )

        ViewModelProviders.of(this, viewModelFactory
            ).get(DiscoverMoviesViewModel::class.java)
    }

    lateinit var binding: FragmentDiscoverMoviesBinding

    private lateinit var adapter: DiscoverMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //Inflate the layout for this fragment
        binding = FragmentDiscoverMoviesBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        configureRecyclerView()
        configureObservables()
        configureOnClick()

        return binding.root

    }

    private fun configureOnClick() {
        binding.fabFilter.setOnClickListener { showDialogWithFilterItems {viewModel.refreshAllList()} }
    }

    private fun showDialogWithFilterItems(callback: () -> Unit) {
        MaterialDialog(this.activity!!)
            .show {
                title(R.string.filter_popup_title)
                listItemsSingleChoice(R.array.filter_list,
                    waitForPositiveButton = false,
                    initialSelection = convertFilterToIndex(viewModel.getFilter())){ _, index, _ ->
                    viewModel.saveFilter(convertIndexToFilter(index))

                }
                positiveButton {
                   // adapter.notifyDataSetChanged()
                    callback()
                }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)

        if (viewModel.currentMovieFilter.value == MovieApiFilter.POPULAR){

            menu.findItem(R.id.action_popular_movies).isChecked = true
        }
        else{
            menu.findItem(R.id.action_top_rated).isChecked = true

        }
    }


    /**
     * Updates the filter in the [OverviewViewModel] when the overflow_menu items are selected from the
     * overflow overflow_menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_popular_movies -> {
                item.isChecked = true
                viewModel.updateMovieFilter(MovieApiFilter.POPULAR)
            }
            R.id.action_top_rated -> {
                item.isChecked = true
                viewModel.updateMovieFilter(MovieApiFilter.TOP_RATED)
            }

            else -> super.onOptionsItemSelected(item)
        }

        return true
    }


    //Configurations

    private fun configureObservables() {
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })

        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(
                    DiscoverMoviesFragmentDirections.actionShowDetail(
                        it.id
                    )
                )
                viewModel.displayMovieDetailsComplete()
            }
        })
    }

    private fun configureRecyclerView() {
         adapter = DiscoverMoviesAdapter(
             this
//
            )

        binding.movieList.adapter = adapter

        val manager = GridLayoutManager(activity, 3)
        binding.movieList.layoutManager = manager


        // draw network status and errors messages to fit the whole row(3 spans)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (adapter.getItemViewType(position)) {
                R.layout.item_network_state -> manager.spanCount
                else -> 1
            }
        }

        val itemDecoration = ItemOffsetDecoration(this.context!!, R.dimen.item_offset)
         binding.movieList.addItemDecoration(itemDecoration)


    }


}
