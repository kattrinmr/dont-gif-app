package com.example.dontgifup.data.api

import com.example.dontgifup.data.models.Post
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface GifService {
    @GET("/random?json=true")
    suspend fun getRandomPost() : Response<Post>
}