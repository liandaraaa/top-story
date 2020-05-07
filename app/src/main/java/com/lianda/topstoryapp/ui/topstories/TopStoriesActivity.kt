package com.lianda.topstoryapp.ui.topstories

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.lianda.topstoryapp.R
import com.lianda.topstoryapp.data.model.Story
import com.lianda.topstoryapp.data.preference.StoryPreference
import com.lianda.topstoryapp.depth.service.model.Resource
import com.lianda.topstoryapp.ui.adapter.StoryAdapter
import com.lianda.topstoryapp.ui.storydetail.StoryDetailActivity
import com.lianda.topstoryapp.ui.storydetail.StoryDetailActivity.Companion.FAVORITE_STORY
import com.lianda.topstoryapp.ui.viewmodel.StoryViewModel
import com.lianda.topstoryapp.utils.gone
import com.lianda.topstoryapp.utils.visible
import kotlinx.android.synthetic.main.activity_top_stories.*
import kotlinx.android.synthetic.main.item_favorite_story.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class TopStoriesActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity<TopStoriesActivity>()
        }
    }

    private val storyViewModel: StoryViewModel by viewModel()

    private val storyAdapter: StoryAdapter by lazy {
        StoryAdapter(this, mutableListOf()) { id ->
            goToDetailActivity(id)
        }
    }

    private val preference: StoryPreference by inject()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_top_stories)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.label_top_stories)

        observeData()

        getTopStories()
        showFavoriteStory()
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

    private fun showFavoriteStory() {
        val favoriteStory = preference.getString(FAVORITE_STORY, "")
        if (favoriteStory.isNotEmpty()) {
            favoriteContainer.visible()
            tvFavoriteTitle.text = favoriteStory
        }else{
            favoriteContainer.gone()
        }
    }

    private fun showStories(stories: List<Story>) {
        storyAdapter.datas.addAll(stories)
        rvStory.apply {
            layoutManager = LinearLayoutManager(this@TopStoriesActivity)
            adapter = storyAdapter
        }
    }

    private fun goToDetailActivity(storyId: Int) {
        StoryDetailActivity.start(this, storyId)
    }

}