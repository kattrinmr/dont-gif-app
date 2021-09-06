package com.example.dontgifup.data.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dontgifup.BR
import com.example.dontgifup.data.models.Post.Companion.TABLE_NAME
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Entity(
    tableName = TABLE_NAME
)
class Post {
    companion object {
        const val TABLE_NAME = "Post"
        const val PRIMARY_ID = "primary_id"
        const val ID = "id"
        const val DESCRIPTION = "description"
        const val GIF_URL = "gifURL"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PRIMARY_ID)
    var primaryId: Int = 0

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = DESCRIPTION)
    var description: String? = null

    @SerializedName("gifURL")
    @Expose
    @ColumnInfo(name = GIF_URL)
    var gifURL: String? = null
}