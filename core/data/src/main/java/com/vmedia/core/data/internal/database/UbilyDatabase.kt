package com.vmedia.core.data.internal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vmedia.core.data.internal.database.converter.*
import com.vmedia.core.data.internal.database.dao.*
import com.vmedia.core.data.internal.database.entity.*

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Asset::class,
        Sale::class,
        Review::class,
        Event::class,
        EventEntity::class,
        Publisher::class,
        User::class,
        Revenue::class,
        Payout::class,
        PeriodWrap::class,
        Keyword::class,
        Artwork::class,
        AssetKeyword::class,
        Category::class
    ]
)
@TypeConverters(
    AssetStatusConverter::class,
    BigDecimalConverter::class,
    CurrencyConverter::class,
    EventTypeConverter::class,
    DateConverter::class,
    MonthConverter::class
)
internal abstract class UbilyDatabase : RoomDatabase() {

    abstract fun getEventDao(): EventDao
    abstract fun getEventEntityDao(): EventEntityDao
    abstract fun getAssetDao(): AssetDao
    abstract fun getArtworkDao(): ArtworkDao
    abstract fun getAssetKeywordDao(): AssetKeywordDao
    abstract fun getKeywordDao(): KeywordDao
    abstract fun getSaleDao(): SaleDao
    abstract fun getPeriodDao(): PeriodDao
    abstract fun getPublisherDao(): PublisherDao
    abstract fun getUserDao(): UserDao
    abstract fun getReviewDao(): ReviewDao
    abstract fun getRevenueDao(): RevenueDao
    abstract fun getPayoutDao(): PayoutDao
    abstract fun getCategoryDao(): CategoryDao

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