package com.portfolio.automoykago.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReviewDao {

    @Insert
    suspend fun insert(review: Review): Long

    @Query("SELECT * FROM reviews ORDER BY createdAt DESC")
    suspend fun getAll(): List<Review>
}
