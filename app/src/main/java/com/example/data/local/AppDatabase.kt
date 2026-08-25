package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.MasterBearingCatalog
import com.example.data.MockBearingCatalog
import com.example.data.local.dao.BearingDao
import com.example.data.local.entity.BearingEntity
import com.example.data.local.entity.InventoryEntity

@Database(
    entities = [BearingEntity::class, InventoryEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bearingDao(): BearingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bearing_hub_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun seedInitialData(bearingDao: BearingDao) {
            // Bulk insert all 268 records from the authoritative Excel template
            val masterBearings = MasterBearingCatalog.allBearings
            bearingDao.insertBearings(masterBearings)

            // Also seed sample workshop inventory stock for initial demo bearings
            val mockEntries = MockBearingCatalog.getAllInitialSeed()
            for (entry in mockEntries) {
                val existingInventory = bearingDao.getInventoryByBearingNumber(entry.bearing.number)
                if (existingInventory.isEmpty()) {
                    val inventoryEntity = InventoryEntity.fromDomainModel(entry.bearing.number, entry.inventory)
                    bearingDao.insertInventoryList(listOf(inventoryEntity))
                }
            }
        }
    }
}
