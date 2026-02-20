package com.portfolio.automoykago.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userName: String,
    val address: String,
    val moduleNumber: Int,
    val services: String,
    val totalAmount: Int,
    val createdAt: Long = System.currentTimeMillis()
)
