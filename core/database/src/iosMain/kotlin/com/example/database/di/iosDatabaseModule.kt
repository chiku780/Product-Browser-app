package com.example.database.di

import com.example.database.dao.DatabaseDriverFactory
import com.example.database.dao.IOSDatabaseDriverFactory
import org.koin.dsl.module

val iosDatabaseModule = module {
    single<DatabaseDriverFactory> {
        IOSDatabaseDriverFactory()
    }
}