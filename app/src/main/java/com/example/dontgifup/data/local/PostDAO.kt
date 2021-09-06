package com.example.dontgifup.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dontgifup.data.models.Post

@Dao
interface PostDAO {

    @Insert
    suspend fun insertPost(post: Post)

    @Query("SELECT * FROM ${Post.TABLE_NAME}")
    suspend fun getAllPosts(): List<Post>

    @Query("SELECT * FROM ${Post.TABLE_NAME} WHERE ${Post.PRIMARY_ID}=(:primaryId + 1)")
    suspend fun getNextCachedPostIfExists(primaryId: Int) : List<Post>

    @Query("SELECT * FROM ${Post.TABLE_NAME} WHERE ${Post.PRIMARY_ID}=(:primaryId - 1)")
    suspend fun getPrevCachedPostIfExists(primaryId: Int) : List<Post>
}