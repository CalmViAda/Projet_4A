package com.example.projet_4a.domain.entity

import com.google.gson.annotations.SerializedName

data class MovieItem (
    val id: Int,
    @SerializedName("poster_path")
    val posterPath:String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String
)
