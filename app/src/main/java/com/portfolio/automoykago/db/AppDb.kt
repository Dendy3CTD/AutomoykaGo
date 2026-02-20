package com.portfolio.automoykago.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

/**
 * Доступ к SQL-базе через сырые запросы.
 */
object AppDb {

    private fun Cursor.col(name: String): Int {
        val i = getColumnIndex(name)
        if (i < 0) throw IllegalArgumentException("column: $name")
        return i
    }

    private const val TABLE_USERS = "users"
    private const val TABLE_ORDERS = "orders"
    private const val TABLE_REVIEWS = "reviews"

    @Volatile
    private var helper: DatabaseHelper? = null

    fun getInstance(context: Context): AppDb {
        if (helper == null) {
            synchronized(this) {
                if (helper == null) {
                    helper = DatabaseHelper(context.applicationContext)
                }
            }
        }
        return this
    }

    private fun db(): SQLiteDatabase = helper!!.writableDatabase

    // --- Users ---
    fun insertUser(context: Context, name: String, password: String, createdAt: Long = System.currentTimeMillis()): Long {
        val cv = ContentValues().apply {
            put("name", name)
            put("password", password)
            put("createdAt", createdAt)
        }
        return db().insert(TABLE_USERS, null, cv)
    }

    fun getUserByName(context: Context, name: String): User? {
        db().rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE name = ? LIMIT 1",
            arrayOf(name)
        ).use { c ->
            if (c.moveToFirst()) {
                return User(
                    id = c.getLong(c.col("id")),
                    name = c.getString(c.col("name")),
                    password = c.getString(c.col("password")),
                    createdAt = c.getLong(c.col("createdAt"))
                )
            }
        }
        return null
    }

    fun getAllUsers(context: Context): List<User> {
        val list = mutableListOf<User>()
        db().rawQuery("SELECT * FROM $TABLE_USERS ORDER BY createdAt DESC", null).use { c ->
            while (c.moveToNext()) {
                list.add(
                    User(
                        id = c.getLong(c.col("id")),
                        name = c.getString(c.col("name")),
                        password = c.getString(c.col("password")),
                        createdAt = c.getLong(c.col("createdAt"))
                    )
                )
            }
        }
        return list
    }

    // --- Orders ---
    fun insertOrder(
        context: Context,
        userName: String,
        address: String,
        moduleNumber: Int,
        services: String,
        totalAmount: Int,
        createdAt: Long = System.currentTimeMillis()
    ): Long {
        val cv = ContentValues().apply {
            put("userName", userName)
            put("address", address)
            put("moduleNumber", moduleNumber)
            put("services", services)
            put("totalAmount", totalAmount)
            put("createdAt", createdAt)
        }
        return db().insert(TABLE_ORDERS, null, cv)
    }

    fun getAllOrders(context: Context): List<Order> {
        val list = mutableListOf<Order>()
        db().rawQuery("SELECT * FROM $TABLE_ORDERS ORDER BY createdAt DESC", null).use { c ->
            while (c.moveToNext()) {
                list.add(
                    Order(
                        id = c.getLong(c.col("id")),
                        userName = c.getString(c.col("userName")),
                        address = c.getString(c.col("address")),
                        moduleNumber = c.getInt(c.col("moduleNumber")),
                        services = c.getString(c.col("services")),
                        totalAmount = c.getInt(c.col("totalAmount")),
                        createdAt = c.getLong(c.col("createdAt"))
                    )
                )
            }
        }
        return list
    }

    fun getTotalRevenue(context: Context): Int {
        db().rawQuery("SELECT COALESCE(SUM(totalAmount), 0) AS total FROM $TABLE_ORDERS", null).use { c ->
            if (c.moveToFirst()) {
                return c.getInt(0)
            }
        }
        return 0
    }

    // --- Reviews ---
    fun insertReview(
        context: Context,
        userName: String,
        text: String,
        rating: Int,
        createdAt: Long = System.currentTimeMillis()
    ): Long {
        val cv = ContentValues().apply {
            put("userName", userName)
            put("text", text)
            put("rating", rating)
            put("createdAt", createdAt)
        }
        return db().insert(TABLE_REVIEWS, null, cv)
    }

    fun getAllReviews(context: Context): List<Review> {
        val list = mutableListOf<Review>()
        db().rawQuery("SELECT * FROM $TABLE_REVIEWS ORDER BY createdAt DESC", null).use { c ->
            while (c.moveToNext()) {
                list.add(
                    Review(
                        id = c.getLong(c.col("id")),
                        userName = c.getString(c.col("userName")),
                        text = c.getString(c.col("text")),
                        rating = c.getInt(c.col("rating")),
                        createdAt = c.getLong(c.col("createdAt"))
                    )
                )
            }
        }
        return list
    }
}
