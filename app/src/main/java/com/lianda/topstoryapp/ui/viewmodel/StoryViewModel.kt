package com.lianda.topstoryapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lianda.topstoryapp.data.model.Comment
import com.lianda.topstoryapp.data.model.Story
import com.lianda.topstoryapp.data.source.RepositoryImpl
import com.lianda.topstoryapp.depth.service.model.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class StoryViewModel(
    private val repository: RepositoryImpl
) : ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val stories = MutableLiveData<Resource<List<Story>>>()
    val story = MutableLiveData<Resource<Story>>()
    val comments = MutableLiveData<Resource<List<Comment>>>()

    fun getTopStories() {
        stories.value = Resource.loading()
        val datas = mutableListOf<Story>()
        uiScope.launch {
            try {
                val storyIds = repository.getTopStories()
                storyIds.forEach { storyId ->
                    val story = repository.getStoryDetail(storyId)
                    datas.add(story)
                }
                stories.value = Resource.success(datas)
            } catch (e: Exception) {
                stories.value = Resource.error(e.message)
                Log.d("Story Exception", e.message.toString())
            }
        }
    }

    fun getStoryDetail(storyId: Int) {
        story.value = Resource.loading()
        uiScope.launch {
            try {
                val data = repository.getStoryDetail(storyId)
                story.value = Resource.success(data)
            } catch (e: Exception) {
                story.value = Resource.error(e.message)
                Log.d("Story Exception", e.message.toString())
            }
        }
    }

    fun getComments(storyId: Int) {
        comments.value = Resource.loading()
        val datas = mutableListOf<Comment>()
        uiScope.launch {
            try {
                val story = repository.getStoryDetail(storyId)
                val commentIds = story.kids.orEmpty()
                commentIds.forEach { commentId ->
                    val comment = repository.getCommentDetail(commentId)
                    datas.add(comment)
                }
                comments.value = Resource.success(datas)
            } catch (e: Exception) {
                story.value = Resource.error(e.message)
                Log.d("Story Exception", e.message.toString())
            }
        }
    }
}