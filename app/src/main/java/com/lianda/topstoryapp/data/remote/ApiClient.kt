package com.lianda.topstoryapp.data.remote

import com.lianda.topstoryapp.data.model.Story
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {
    @GET("topstories.json?print=pretty")
    suspend fun getTopStories():List<Int>

    @GET("item/{storyId}.json?print=pretty")
    suspend fun getStoryDetail(@Path("id") storyId:Int): Story
}