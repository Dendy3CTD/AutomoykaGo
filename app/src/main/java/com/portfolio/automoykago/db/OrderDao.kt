package com.portfolio.automoykago.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {

    @Insert
    suspend fun insert(order: Order): Long

    @Query("SELECT * FROM orders ORDER BY createdAt DESC")
    suspend fun getAll(): List<Order>

    @Query("SELECT COALESCE(SUM(totalAmount), 0) FROM orders")
    suspend fun getTotalRevenue(): Int

    @Query("SELECT * FROM orders WHERE createdAt BETWEEN :from AND :to ORDER BY createdAt DESC")
    suspend fun getByDateRange(from: Long, to: Long): List<Order>
}
