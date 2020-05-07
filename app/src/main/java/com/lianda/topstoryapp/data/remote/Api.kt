package com.lianda.topstoryapp.data.remote

import com.lianda.topstoryapp.data.model.Story

class Api (private val apiClient: ApiClient): ApiClient{
    override suspend fun getTopStories(): List<Int> = apiClient.getTopStories()

    override suspend fun getStoryDetail(storyId: Int): Story = apiClient.getStoryDetail(storyId)

}