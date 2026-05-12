package com.example.database.dao

import app.cash.sqldelight.db.SqlDriver
import com.example.core.database.AppDatabase

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

class LocalDatabase(
    databaseDriverFactory: DatabaseDriverFactory
) {
    val database = AppDatabase(
        databaseDriverFactory.createDriver()
    )
}