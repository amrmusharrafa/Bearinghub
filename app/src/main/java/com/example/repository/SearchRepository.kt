package com.example.repository

import com.example.model.Bearing
import com.example.model.Inventory
import com.example.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchBearing(number: String): Result<SearchResponse>
    suspend fun saveBearingDetails(bearing: Bearing, inventoryList: List<Inventory>): Result<Unit>
    fun getBearingFlow(number: String): Flow<SearchResponse?>
    fun getAllBearingsFlow(): Flow<List<Bearing>>
}
