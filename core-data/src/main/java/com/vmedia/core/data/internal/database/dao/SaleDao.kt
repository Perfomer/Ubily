package com.vmedia.core.data.internal.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vmedia.core.data.internal.database.entity.Sale
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface SaleDao {

    @Query("SELECT * FROM Sale ORDER BY date DESC")
    fun getProvisions(): Observable<List<Sale>>

    @Query("SELECT * FROM Sale WHERE price > 0 ORDER BY date DESC")
    fun getPaidSales(): Observable<List<Sale>>

    @Query("SELECT * FROM Sale WHERE price = 0 ORDER BY date DESC")
    fun getFreeDownloads(): Observable<List<Sale>>

    @Query("SELECT * FROM Sale WHERE date BETWEEN :startTimestamp AND :endTimestamp ORDER BY date DESC")
    fun getProvisionsByPeriod(startTimestamp: Long, endTimestamp: Long): Observable<List<Sale>>

    @Query(
        """
        SELECT * FROM Sale 
        WHERE 
            date BETWEEN :startTimestamp AND :endTimestamp
            AND price > 0
        ORDER BY date DESC
        """
    )
    fun getPaidSalesByPeriod(startTimestamp: Long, endTimestamp: Long): Observable<List<Sale>>

    @Query(
        """
        SELECT * FROM Sale 
        WHERE 
            date BETWEEN :startTimestamp AND :endTimestamp
            AND price = 0
        ORDER BY date DESC
        """
    )
    fun getFreeDownloadsByPeriod(startTimestamp: Long, endTimestamp: Long): Observable<List<Sale>>

    @Query("SELECT SUM(price * quantity) FROM Sale")
    fun getSalesAmount(): Single<Int>

    @Query("SELECT SUM(price * quantity) FROM Sale WHERE date BETWEEN :startTimestamp AND :endTimestamp")
    fun getSalesAmountByPeriod(startTimestamp: Long, endTimestamp: Long): Single<Int>

}