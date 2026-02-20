package com.portfolio.automoykago.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userName: String,
    val text: String,
    val rating: Int,
    val createdAt: Long = System.currentTimeMillis()
)
