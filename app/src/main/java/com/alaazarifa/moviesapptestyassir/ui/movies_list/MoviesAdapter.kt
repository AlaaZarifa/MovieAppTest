package com.alaazarifa.moviesapptestyassir.ui.movies_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alaazarifa.moviesapptestyassir.data.api.IMAGE_URL
import com.alaazarifa.moviesapptestyassir.data.model.Movie
import com.alaazarifa.moviesapptestyassir.databinding.MovieCardBinding
import com.bumptech.glide.Glide
import java.util.Locale

class MoviesAdapter(
    private val articleList: ArrayList<Movie>,
    private val onClick: (Long) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.DataViewHolder>() {

    class DataViewHolder( val binding: MovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                titleTV.text = movie.title
                yearTV.text = movie.releaseDate.substringBefore("-")
                ratingTV.text = String.format(Locale.US, "%.1f", movie.voteAverage)
                val photoUrl = "$IMAGE_URL${movie.posterPath}"
                Glide.with(imageView.context)
                    .load(photoUrl)
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            MovieCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(articleList[position])
        holder.itemView.setOnClickListener {
            onClick(articleList[position].id)
        }

    }

    fun addData(list: List<Movie>) {
        articleList.clear()
        articleList.addAll(list)
    }

}