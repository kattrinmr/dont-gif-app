package com.example.dontgifup.domain.repositories

import com.example.dontgifup.data.models.Post

interface GifRepository {

    suspend fun getNextRandomGif(currentPrimaryId: Int) : Post
    suspend fun getPrevGif(currentPrimaryId: Int) : Post

//    suspend fun getAllGifs(): List<Post>
}