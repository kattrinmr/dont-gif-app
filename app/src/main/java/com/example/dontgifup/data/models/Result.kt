package com.example.dontgifup.data.models
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {
    @SerializedName("result")
    @Expose
    private val result: List<Post>? = null

    @SerializedName("totalCount")
    @Expose
    private val totalCount: Int? = null
}