package com.example.dontgifup.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dontgifup.data.models.Post

@Database(
    entities = [Post::class], version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun postDAO(): PostDAO

    companion object {
        const val DATABASE_NAME = "post.db"

        fun buildDataBase(context: Context, dbName: String): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, dbName).build()
        }
    }
}