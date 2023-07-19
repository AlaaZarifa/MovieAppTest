package com.alaazarifa.moviesapptestyassir.data.model


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: Long,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)