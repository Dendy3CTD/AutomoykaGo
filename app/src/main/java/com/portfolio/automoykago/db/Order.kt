package com.portfolio.automoykago.db

data class Order(
    val id: Long = 0,
    val userName: String,
    val address: String,
    val moduleNumber: Int,
    val services: String,
    val totalAmount: Int,
    val createdAt: Long = System.currentTimeMillis()
)
