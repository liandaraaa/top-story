package com.lianda.topstoryapp.ui.topstories

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.lianda.topstoryapp.R
import com.lianda.topstoryapp.data.model.Story
import com.lianda.topstoryapp.depth.service.model.Resource
import com.lianda.topstoryapp.ui.groupie.HeaderSection
import com.lianda.topstoryapp.ui.groupie.StoryContentSection
import com.lianda.topstoryapp.ui.viewmodel.StoryViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_top_stories.*
import kotlinx.android.synthetic.main.layout_error.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel

class TopStoriesActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity<TopStoriesActivity>()
        }
    }

    private val storyViewModel: StoryViewModel by viewModel()

    private val groupieAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_top_stories)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.label_top_stories)

        observeData()

        rvStory.apply {
            layoutManager = LinearLayoutManager(this@TopStoriesActivity)
            adapter = groupieAdapter
        }

        getTopStories()
    }

    private fun getTopStories() {
        storyViewModel.getTopStories()
    }

    private fun observeData() {
        storyViewModel.stories.observe(this, Observer {
            when (it) {
                is Resource.Loading -> msvStory.viewState = MultiStateView.ViewState.LOADING
                is Resource.Empty -> msvStory.viewState = MultiStateView.ViewState.EMPTY
                is Resource.Success -> {
                    msvStory.viewState = MultiStateView.ViewState.CONTENT
                    showStories(it.data)
                }
                is Resource.Error -> {
                    msvStory.viewState = MultiStateView.ViewState.ERROR
                    btnRetry.setOnClickListener {
                        storyViewModel.getTopStories()
                    }
                }
            }
        }
        )
    }

    private fun showFavoriteStories(stories: List<Story>) {
        groupieAdapter.add(HeaderSection("Favorite Stories"))
        groupieAdapter.add(
            StoryContentSection(
                context = this,
                datas = stories
            )
        )
    }

    private fun showStories(stories: List<Story>) {
        groupieAdapter.add(
            StoryContentSection(
                context = this,
                datas = stories
            )
        )
    }

}