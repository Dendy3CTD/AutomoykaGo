package com.portfolio.automoykago.db

data class Review(
    val id: Long = 0,
    val userName: String,
    val text: String,
    val rating: Int,
    val createdAt: Long = System.currentTimeMillis()
)
