package com.example.database.di

import com.example.database.dao.LocalDatabase
import com.example.database.dao.ProductListDao
import org.koin.dsl.module

val dataBaseModule = module {
    single<LocalDatabase> { LocalDatabase(get()) }
    single { get<LocalDatabase>().database }
    single { ProductListDao(get()) }
}