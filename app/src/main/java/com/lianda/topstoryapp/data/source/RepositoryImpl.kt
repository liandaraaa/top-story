package com.lianda.topstoryapp.data.source

import com.lianda.topstoryapp.data.model.Story

interface RepositoryImpl{
    suspend fun getTopStories():List<Int>
    suspend fun getStoryDetail(storyId:Int):Story
}