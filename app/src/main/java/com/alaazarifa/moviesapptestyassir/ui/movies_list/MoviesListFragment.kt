package com.alaazarifa.moviesapptestyassir.ui.movies_list

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alaazarifa.moviesapptestyassir.R
import com.alaazarifa.moviesapptestyassir.data.api.client.UiState
import com.alaazarifa.moviesapptestyassir.data.model.Movie
import com.alaazarifa.moviesapptestyassir.databinding.FragmentMoviesListBinding
import com.alaazarifa.moviesapptestyassir.ui.base.HostActivity
import com.alaazarifa.moviesapptestyassir.ui.base.ViewModelProviderFactory
import kotlinx.coroutines.launch

class MoviesListFragment : Fragment() {

    private lateinit var binding: FragmentMoviesListBinding

    lateinit var viewModel: MoviesListViewModel

    lateinit var adapter: MoviesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set the status-bar color
        (activity as HostActivity).setStatusBarTransparent(false)

        // initializing the ViewModel
        viewModel = ViewModelProvider(this, ViewModelProviderFactory(MoviesListViewModel::class) {
            MoviesListViewModel(
                Application()
            )
        })[MoviesListViewModel::class.java]

        // setting-up UI elements
        setupUI()

        // listening to the API response
        setupObserver()
    }


    private fun setupUI() {

        // setting the list adapter
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = MoviesAdapter(arrayListOf()) { movieID ->

                // handling item click action
                findNavController().navigate(
                    R.id.action_movieDetails,
                    bundleOf("movieID" to movieID),
                )

            }
            recyclerView.adapter = adapter
        }

    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            renderList(it.data)
                            binding.shimmerView.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            binding.shimmerView.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            binding.shimmerView.visibility = View.GONE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(articleList: List<Movie>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

}