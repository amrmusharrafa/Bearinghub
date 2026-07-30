package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.entity.BearingEntity
import com.example.data.local.entity.InventoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BearingDao {

    @Query("SELECT * FROM bearings WHERE UPPER(number) = UPPER(:number) OR UPPER(:number) LIKE UPPER(number) || '%' OR UPPER(number) LIKE UPPER(:number) || '%' LIMIT 1")
    suspend fun getBearingByNumber(number: String): BearingEntity?

    @Query("SELECT * FROM bearings WHERE number = :number LIMIT 1")
    fun getBearingByNumberFlow(number: String): Flow<BearingEntity?>

    @Query("SELECT * FROM inventories WHERE bearingNumber = :bearingNumber")
    suspend fun getInventoryByBearingNumber(bearingNumber: String): List<InventoryEntity>

    @Query("SELECT * FROM inventories WHERE bearingNumber = :bearingNumber")
    fun getInventoryByBearingNumberFlow(bearingNumber: String): Flow<List<InventoryEntity>>

    @Query("SELECT * FROM bearings ORDER BY number ASC")
    fun getAllBearingsFlow(): Flow<List<BearingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBearing(bearing: BearingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventoryList(inventories: List<InventoryEntity>)

    @Query("DELETE FROM inventories WHERE bearingNumber = :bearingNumber")
    suspend fun deleteInventoryByBearingNumber(bearingNumber: String)

    @Transaction
    suspend fun upsertBearingWithInventory(
        bearing: BearingEntity,
        inventories: List<InventoryEntity>
    ) {
        insertBearing(bearing)
        deleteInventoryByBearingNumber(bearing.number)
        insertInventoryList(inventories)
    }

    @Query("SELECT COUNT(*) FROM bearings")
    suspend fun getBearingsCount(): Int
}
