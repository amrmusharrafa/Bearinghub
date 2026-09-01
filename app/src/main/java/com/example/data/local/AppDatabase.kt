package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.MasterBearingCatalog
import com.example.data.MockBearingCatalog
import com.example.data.local.dao.BearingDao
import com.example.data.local.entity.BearingEntity
import com.example.data.local.entity.InventoryEntity

@Database(
    entities = [BearingEntity::class, InventoryEntity::class],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bearingDao(): BearingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE bearings ADD COLUMN bearingType TEXT NOT NULL DEFAULT 'Deep Groove Ball Bearings'")
                db.execSQL("UPDATE bearings SET manufacturer = 'SKF' WHERE manufacturer = 'ISO / DIN Standard' OR manufacturer IS NULL OR manufacturer = ''")
                db.execSQL("ALTER TABLE inventories ADD COLUMN currency TEXT NOT NULL DEFAULT 'USD'")
            }
        }

        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("UPDATE bearings SET manufacturer = 'SKF', bearingType = 'Deep Groove Ball Bearings'")
                db.execSQL("CREATE TABLE IF NOT EXISTS inventories_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, bearingNumber TEXT NOT NULL, condition TEXT NOT NULL, quantity INTEGER, sellingPrice REAL, shelfLocation TEXT NOT NULL, currency TEXT NOT NULL, FOREIGN KEY(bearingNumber) REFERENCES bearings(number) ON DELETE CASCADE)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_inventories_new_bearingNumber ON inventories_new(bearingNumber)")
                db.execSQL("INSERT INTO inventories_new (id, bearingNumber, condition, quantity, sellingPrice, shelfLocation, currency) SELECT id, bearingNumber, condition, NULL, NULL, shelfLocation, currency FROM inventories")
                db.execSQL("DROP TABLE inventories")
                db.execSQL("ALTER TABLE inventories_new RENAME TO inventories")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bearing_hub_database"
                )
                .addMigrations(MIGRATION_5_6, MIGRATION_6_7)
                .fallbackToDestructiveMigrationOnDowngrade()
                .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun seedInitialData(bearingDao: BearingDao) {
            // Bulk insert all 268 records from the authoritative Excel template
            val masterBearings = MasterBearingCatalog.allBearings
            bearingDao.insertBearings(masterBearings)
        }
    }
}
