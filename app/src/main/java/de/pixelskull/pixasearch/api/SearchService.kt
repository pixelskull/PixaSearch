package de.pixelskull.pixasearch.api

import de.pixelskull.pixasearch.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface  SearchService {
    @GET("api/?key=24657931-54b60ba78c78ec1a5b49e040e")
    suspend fun search(@Query("q") searchTerm: String): SearchResponse
}