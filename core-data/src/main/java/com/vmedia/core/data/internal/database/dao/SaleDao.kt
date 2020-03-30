package com.vmedia.core.data.internal.database.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.dao.base.BaseDao
import com.vmedia.core.data.internal.database.entity.Sale
import io.reactivex.Observable
import io.reactivex.Single
import java.math.BigDecimal
import java.util.*

@Dao
interface SaleDao : BaseDao<Sale> {

    @Query("SELECT * FROM Sale ORDER BY date DESC")
    fun getProvisions(): Observable<List<Sale>>

    @Query("SELECT * FROM Sale WHERE priceUsd > 0 ORDER BY date DESC")
    fun getPaidSales(): Observable<List<Sale>>

    @Query("SELECT * FROM Sale WHERE priceUsd = 0 ORDER BY date DESC")
    fun getFreeDownloads(): Observable<List<Sale>>

    @Query("SELECT * FROM Sale WHERE date BETWEEN :startTimestamp AND :endTimestamp ORDER BY date DESC")
    fun getProvisionsByPeriod(startTimestamp: Long, endTimestamp: Long): Observable<List<Sale>>

    @Query(
        """
        SELECT * FROM Sale 
        WHERE 
            date BETWEEN :startTimestamp AND :endTimestamp
            AND priceUsd > 0
        ORDER BY date DESC
        """
    )
    fun getPaidSalesByPeriod(startTimestamp: Long, endTimestamp: Long): Observable<List<Sale>>

    @Query(
        """
        SELECT * FROM Sale 
        WHERE 
            date BETWEEN :startTimestamp AND :endTimestamp
            AND priceUsd = 0
        ORDER BY date DESC
        """
    )
    fun getFreeDownloadsByPeriod(startTimestamp: Long, endTimestamp: Long): Observable<List<Sale>>

    @Query("SELECT SUM(priceUsd * quantity) FROM Sale")
    fun getSalesAmount(): Single<Int>

    @Query("SELECT SUM(priceUsd * quantity) FROM Sale WHERE date BETWEEN :startTimestamp AND :endTimestamp")
    fun getSalesAmountByPeriod(startTimestamp: Long, endTimestamp: Long): Single<Int>

    @Query(
        """
            SELECT * FROM Sale 
            WHERE
                assetId = :assetId
                AND priceUsd = :price
                AND date BETWEEN :startTimestamp AND :endTimestamp
            ORDER BY date DESC
            LIMIT 1
        """
    )
    fun getLastSale(
        assetId: Long,
        price: BigDecimal,
        startTimestamp: Long,
        endTimestamp: Long
    ): Single<Sale>

    @WorkerThread
    @Query(
        """
            SELECT id FROM Sale
            WHERE
                assetId = :assetId
                AND priceUsd = :priceUsd
                AND date = :date
        """
    )
    fun getSaleId(assetId: Long, priceUsd: BigDecimal, date: Date): Long

}