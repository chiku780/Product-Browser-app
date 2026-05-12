package com.example.database.di

import com.example.database.dao.AndroidDatabaseDriverFactory
import com.example.database.dao.DatabaseDriverFactory
import org.koin.dsl.module

val androidDatastoreModule = module {
    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(get())
    }
}