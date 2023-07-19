package com.alaazarifa.moviesapptestyassir.ui.movie_details

import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alaazarifa.moviesapptestyassir.data.api.IMAGE_URL
import com.alaazarifa.moviesapptestyassir.data.api.client.UiState
import com.alaazarifa.moviesapptestyassir.data.model.Movie
import com.alaazarifa.moviesapptestyassir.databinding.FragmentMovieDetailsBinding
import com.alaazarifa.moviesapptestyassir.ui.base.HostActivity
import com.alaazarifa.moviesapptestyassir.ui.base.ViewModelProviderFactory
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import java.util.Locale

class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailsBinding

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set the status-bar color
        (activity as HostActivity).setStatusBarTransparent(true)

        // initializing the ViewModel
        viewModel = ViewModelProvider(this, ViewModelProviderFactory(MovieDetailsViewModel::class) { MovieDetailsViewModel(Application()) })[MovieDetailsViewModel::class.java]

        // listening to the API response
        setupObserver()

        // sending the API request
        viewModel.fetchDetails(args.movieID)
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {

                            // the postDelayed Handler here is only because the API response
                            // is received very fast and the shimmer animation won't be noticeable.
                            // In a real project, this would be omitted and won't be used like this.

                            Handler(Looper.getMainLooper()).postDelayed({
                                binding.shimmerView.visibility = View.GONE
                                binding.contentView.visibility = View.VISIBLE
                                bindData(it.data.first())
                            },250)

                        }

                        is UiState.Loading -> {
                            binding.contentView.visibility = View.GONE
                            binding.shimmerView.visibility = View.VISIBLE
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

    private fun bindData(movie: Movie) {
        binding.apply {
            movie.apply {
                titleTV.text =title
                overviewTV.text = overview

                // showing only the year
                yearTV.text =releaseDate.substringBefore("-")

                // showing only one digit after the decimal point without rounding
                ratingTV.text = String.format(Locale.US, "%.1f", voteAverage)

                backButton.setOnClickListener {
                    findNavController().navigateUp()
                }

                // forming the correct url for the image
                val photoUrl = "$IMAGE_URL${movie.posterPath}"
                Glide.with(imageView.context)
                    .load(photoUrl)
                    .into(imageView);
            }
        }
    }


}