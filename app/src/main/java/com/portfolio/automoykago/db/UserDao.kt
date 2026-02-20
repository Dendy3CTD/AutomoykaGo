package com.portfolio.automoykago.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): User?

    @Query("SELECT * FROM users ORDER BY createdAt DESC")
    suspend fun getAll(): List<User>
}
