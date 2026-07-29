package com.example.repository

import com.example.model.SearchResponse

interface SearchRepository {
    suspend fun searchBearing(number: String): Result<SearchResponse>
}
