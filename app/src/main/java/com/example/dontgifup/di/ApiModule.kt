package com.example.dontgifup.di

import com.example.dontgifup.GifBaseUrl
import com.example.dontgifup.data.api.GifService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .build()

    @Provides
    @Singleton
    fun provideGifRetrofit(gson: Gson, okHttpClient: OkHttpClient): GifService =
        Retrofit.Builder()
            .baseUrl(GifBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(GifService::class.java)



}
