package com.portfolio.automoykago.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * База данных на SQL (SQLite).
 * Таблицы создаются сырыми SQL-запросами.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_USERS)
        db.execSQL(SQL_CREATE_ORDERS)
        db.execSQL(SQL_CREATE_REVIEWS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL(SQL_CREATE_ORDERS)
        }
        if (oldVersion < 3) {
            db.execSQL(SQL_CREATE_REVIEWS)
        }
    }

    companion object {
        private const val DB_NAME = "automoykago.db"
        private const val DB_VERSION = 3

        private const val SQL_CREATE_USERS = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                password TEXT NOT NULL,
                createdAt INTEGER NOT NULL DEFAULT 0
            )
        """

        private const val SQL_CREATE_ORDERS = """
            CREATE TABLE IF NOT EXISTS orders (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                userName TEXT NOT NULL,
                address TEXT NOT NULL,
                moduleNumber INTEGER NOT NULL,
                services TEXT NOT NULL,
                totalAmount INTEGER NOT NULL,
                createdAt INTEGER NOT NULL DEFAULT 0
            )
        """

        private const val SQL_CREATE_REVIEWS = """
            CREATE TABLE IF NOT EXISTS reviews (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                userName TEXT NOT NULL,
                text TEXT NOT NULL,
                rating INTEGER NOT NULL,
                createdAt INTEGER NOT NULL DEFAULT 0
            )
        """
    }
}
