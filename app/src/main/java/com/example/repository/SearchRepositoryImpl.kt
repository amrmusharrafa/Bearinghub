package com.example.repository

import com.example.model.SearchResponse
import com.example.network.BearingApiService

class SearchRepositoryImpl(
    private val apiService: BearingApiService
) : SearchRepository {

    override suspend fun searchBearing(number: String): Result<SearchResponse> {
        return try {
            val response = apiService.searchBearing(number)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else if (response.code() == 404) {
                Result.success(SearchResponse(success = false, data = null, message = "Bearing not found"))
            } else {
                Result.failure(Exception("HTTP error ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
