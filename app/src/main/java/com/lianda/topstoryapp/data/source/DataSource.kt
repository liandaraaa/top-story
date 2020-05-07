package com.lianda.topstoryapp.data.source

import com.lianda.topstoryapp.data.model.Story
import com.lianda.topstoryapp.data.remote.Api

class DataSource(val api: Api) : RepositoryImpl {

    override suspend fun getTopStories(): List<Int> {
        return api.getTopStories()
    }

    override suspend fun getStoryDetail(storyId: Int): Story {
        return api.getStoryDetail(storyId)
    }

}