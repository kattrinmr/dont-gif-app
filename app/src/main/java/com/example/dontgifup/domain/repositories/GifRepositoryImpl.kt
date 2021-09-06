package com.example.dontgifup.domain.repositories

import com.example.dontgifup.data.api.GifService
import com.example.dontgifup.data.local.PostDAO
import com.example.dontgifup.data.models.Post
import com.example.dontgifup.errors.CacheException
import com.example.dontgifup.errors.ServerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class GifRepositoryImpl(private val gifService: GifService, private val postDAO: PostDAO) : GifRepository {

    // TODO: check internet connection

    // TODO: fetch gifs from different categories



    private suspend fun fetchRandomGif() : Response<Post> = withContext(Dispatchers.IO) {
        return@withContext gifService.getRandomPost()
    }

    private suspend fun cacheGif(post: Post) {
        postDAO.insertPost(post)
    }

    override suspend fun getPrevGif(currentPrimaryId: Int): Post {
        val cached = postDAO.getNextCachedPostIfExists(currentPrimaryId)
        if (cached.isNotEmpty()) return cached[0]
        else throw CacheException("No saved data...")
    }

    override suspend fun getNextRandomGif(currentPrimaryId: Int) : Post {
        val cached = postDAO.getNextCachedPostIfExists(currentPrimaryId)
        if (cached.isNotEmpty()) return cached[0]
        val randomGifResponse = fetchRandomGif()
        if (randomGifResponse.isSuccessful) {
            cacheGif(randomGifResponse.body()!!)
            return randomGifResponse.body()!!
        } else {
            throw ServerException(randomGifResponse.code(), randomGifResponse.message())
        }
    }

//    override suspend fun getAllGifs(): List<Post> = postDAO.getAllPosts()
}
