package com.example.repository

import com.example.data.MasterBearingCatalog
import com.example.data.MockBearingCatalog
import com.example.data.local.AppDatabase
import com.example.data.local.dao.BearingDao
import com.example.data.local.entity.BearingEntity
import com.example.data.local.entity.InventoryEntity
import com.example.model.Bearing
import com.example.model.BearingData
import com.example.model.Inventory
import com.example.model.SearchResponse
import com.example.network.BearingApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl(
    private val bearingDao: BearingDao,
    private val apiService: BearingApiService
) : SearchRepository {

    override suspend fun searchBearing(number: String): Result<SearchResponse> {
        val cleanNumber = number.trim().uppercase()
        if (cleanNumber.isEmpty()) {
            return Result.success(SearchResponse(success = false, data = null, message = "Please enter a valid bearing designation."))
        }

        return try {
            // Ensure initial seeding of the complete 268-bearing master catalog
            if (bearingDao.getBearingsCount() < MasterBearingCatalog.allBearings.size) {
                AppDatabase.seedInitialData(bearingDao)
            }

            // 1. Search in local Room DB
            val localBearing = bearingDao.getBearingByNumber(cleanNumber)
            if (localBearing != null) {
                val inventoryEntities = bearingDao.getInventoryByBearingNumber(localBearing.number)
                val searchResponse = SearchResponse(
                    success = true,
                    data = BearingData(
                        bearing = localBearing.toDomainModel(),
                        inventory = inventoryEntities.map { it.toDomainModel() }
                    )
                )
                return Result.success(searchResponse)
            }

            // 2. Check Mock Catalog locally before external API call
            val catalogMatch = MockBearingCatalog.findBearing(cleanNumber)
            if (catalogMatch != null) {
                saveBearingDetails(catalogMatch.bearing, listOf(catalogMatch.inventory))
                return Result.success(
                    SearchResponse(
                        success = true,
                        data = BearingData(
                            bearing = catalogMatch.bearing,
                            inventory = listOf(catalogMatch.inventory)
                        )
                    )
                )
            }

            // 3. Fallback to API call with safe exception handling (avoids raw network error screens)
            try {
                val response = apiService.searchBearing(cleanNumber)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success && body.data != null) {
                        saveBearingDetails(body.data.bearing, body.data.inventory)
                        Result.success(body)
                    } else {
                        Result.success(SearchResponse(success = false, data = null, message = "Bearing $cleanNumber not found in inventory."))
                    }
                } else {
                    Result.success(SearchResponse(success = false, data = null, message = "Bearing $cleanNumber not found."))
                }
            } catch (networkException: Exception) {
                // If offline or network call fails, present clean "Not Found" rather than network error screen
                Result.success(SearchResponse(success = false, data = null, message = "Bearing $cleanNumber not found."))
            }
        } catch (e: Exception) {
            Result.success(SearchResponse(success = false, data = null, message = "Bearing $cleanNumber not found."))
        }
    }

    override suspend fun saveBearingDetails(
        bearing: Bearing,
        inventoryList: List<Inventory>
    ): Result<Unit> {
        return try {
            val bearingEntity = BearingEntity.fromDomainModel(bearing)
            val inventoryEntities = inventoryList.map {
                InventoryEntity.fromDomainModel(bearing.number, it)
            }
            bearingDao.upsertBearingWithInventory(bearingEntity, inventoryEntities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getBearingFlow(number: String): Flow<SearchResponse?> {
        val cleanNumber = number.trim().uppercase()
        val bearingFlow = bearingDao.getBearingByNumberFlow(cleanNumber)
        val inventoryFlow = bearingDao.getInventoryByBearingNumberFlow(cleanNumber)

        return combine(bearingFlow, inventoryFlow) { bearingEntity, inventoryEntities ->
            if (bearingEntity != null) {
                SearchResponse(
                    success = true,
                    data = BearingData(
                        bearing = bearingEntity.toDomainModel(),
                        inventory = inventoryEntities.map { it.toDomainModel() }
                    )
                )
            } else {
                null
            }
        }
    }

    override fun getAllBearingsFlow(): Flow<List<Bearing>> {
        return bearingDao.getAllBearingsFlow().map { list ->
            list.map { it.toDomainModel() }
        }
    }
}
