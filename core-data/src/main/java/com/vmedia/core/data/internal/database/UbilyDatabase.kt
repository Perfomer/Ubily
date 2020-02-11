package com.vmedia.core.data.internal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vmedia.core.data.internal.database.converter.*
import com.vmedia.core.data.internal.database.dao.AssetDao
import com.vmedia.core.data.internal.database.dao.EventDao
import com.vmedia.core.data.internal.database.dao.SaleDao
import com.vmedia.core.data.internal.database.entity.*

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Asset::class,
        Sale::class,
        Event::class,
        EventEntity::class,
        Publisher::class,
        User::class
    ]
)
@TypeConverters(
    AssetStatusConverter::class,
    BigDecimalConverter::class,
    CurrencyConverter::class,
    EventTypeConverter::class,
    DateConverter::class
)
internal abstract class UbilyDatabase : RoomDatabase() {

    abstract fun getEventDao(): EventDao
    abstract fun getAssetDao(): AssetDao
    abstract fun getSaleDao(): SaleDao

    internal companion object {

        @Synchronized
        internal fun getInstance(
            appContext: Context,
            databaseName: String,
            inMemory: Boolean = false
        ): UbilyDatabase {
            return if (inMemory) {
                Room.inMemoryDatabaseBuilder(appContext, UbilyDatabase::class.java).build()
            } else {
                Room.databaseBuilder(appContext, UbilyDatabase::class.java, databaseName)
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }

    }

}