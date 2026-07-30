package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.MockBearingCatalog
import com.example.data.local.dao.BearingDao
import com.example.data.local.entity.BearingEntity
import com.example.data.local.entity.InventoryEntity

@Database(
    entities = [BearingEntity::class, InventoryEntity::class],
    version = 1,
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
            val seedEntries = MockBearingCatalog.getAllInitialSeed()
            for (entry in seedEntries) {
                val existing = bearingDao.getBearingByNumber(entry.bearing.number)
                if (existing == null) {
                    val bearingEntity = BearingEntity.fromDomainModel(entry.bearing)
                    val inventoryEntity = InventoryEntity.fromDomainModel(entry.bearing.number, entry.inventory)
                    bearingDao.insertBearing(bearingEntity)
                    bearingDao.insertInventoryList(listOf(inventoryEntity))
                }
            }
        }
    }
}
