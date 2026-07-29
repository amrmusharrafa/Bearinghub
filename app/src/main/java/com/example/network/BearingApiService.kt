package com.example.network

import com.example.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BearingApiService {
    @GET("api/v1/bearings/{bearingNumber}")
    suspend fun searchBearing(
        @Path("bearingNumber") bearingNumber: String
    ): Response<SearchResponse>
}
