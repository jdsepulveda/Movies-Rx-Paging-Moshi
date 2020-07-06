package com.movies_rxjava.app.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.movies_rxjava.R
import com.movies_rxjava.app.adapter.PopularMoviesAdapter
import com.movies_rxjava.app.di.MovieApplication
import com.movies_rxjava.app.utils.appBarNavConfiguration
import com.movies_rxjava.app.utils.extensions.gone
import com.movies_rxjava.app.utils.extensions.visible
import com.movies_rxjava.app.viewmodels.FragmentPopularMoviesVM
import com.movies_rxjava.app.viewmodels.factory.ViewModelFactory
import com.movies_rxjava.databinding.FragmentPopularMoviesBinding
import com.movies_rxjava.remote.model.Movie
import kotlinx.android.synthetic.main.fragment_popular_movies.*
import javax.inject.Inject

class FragmentPopularMovies : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var fragmentPopularMoviesVM: FragmentPopularMoviesVM

    private lateinit var popularMoviesAdapter: PopularMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPopularMoviesVM = ViewModelProvider(this, viewModelFactory).get(FragmentPopularMoviesVM::class.java)

        return DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, R.layout.fragment_popular_movies, container, false
        ).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataBindingUtil.findBinding<FragmentPopularMoviesBinding>(view)?.apply {
            viewModel = fragmentPopularMoviesVM
            lifecycleOwner = viewLifecycleOwner
        }

        NavigationUI.setupWithNavController(
            popular_movies_toolbar,
            findNavController(),
            appBarNavConfiguration
        )

        progressBarLoading.visible()
        initRecyclerView()
        setUpDataObservers()
    }

    private fun initRecyclerView() {
        popularMoviesAdapter = PopularMoviesAdapter { movieItem: Movie -> movieItemClicked(movieItem) }
        rvPopularMovies.setHasFixedSize(true)
        rvPopularMovies.adapter = popularMoviesAdapter
    }

    private fun movieItemClicked(movieItem: Movie) {
        Log.d("In movieItemClicked", movieItem.overview)
    }

    private fun setUpDataObservers() {
        fragmentPopularMoviesVM.popularMoviesList.observe(viewLifecycleOwner, Observer {
            progressBarLoading.gone()
            popularMoviesAdapter.submitList(it)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as MovieApplication).appComponent.inject(this)
    }
}