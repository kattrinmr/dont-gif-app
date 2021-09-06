package com.example.dontgifup.di

import android.content.Context
import com.example.dontgifup.data.api.GifService
import com.example.dontgifup.data.local.AppDataBase
import com.example.dontgifup.domain.repositories.GifRepository
import com.example.dontgifup.domain.repositories.GifRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    private var appContext: Context
) {

    @Provides @Singleton fun provideContext() : Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideAppDataBase(applicationContext: Context) : AppDataBase =
        AppDataBase.buildDataBase(applicationContext, AppDataBase.DATABASE_NAME)

    @Provides
    @Singleton
    fun provideGifRepository(gifService: GifService, appDataBase: AppDataBase) : GifRepository =
        GifRepositoryImpl(gifService, appDataBase.postDAO())
}