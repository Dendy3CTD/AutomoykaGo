package com.portfolio.automoykago.db

data class User(
    val id: Long = 0,
    val name: String,
    val password: String,
    val createdAt: Long = System.currentTimeMillis()
)
