package com.example.projet_4a.domain.entity

import com.google.gson.annotations.SerializedName

data class MovieDataResponse(
    val page: Int,
    @SerializedName("results")
    val movieList: List<MovieItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)