package com.lianda.topstoryapp.ui.storydetail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.lianda.topstoryapp.R
import com.lianda.topstoryapp.data.model.Comment
import com.lianda.topstoryapp.data.model.Story
import com.lianda.topstoryapp.data.preference.StoryPreference
import com.lianda.topstoryapp.depth.service.model.Resource
import com.lianda.topstoryapp.ui.adapter.CommentAdapter
import com.lianda.topstoryapp.ui.viewmodel.StoryViewModel
import kotlinx.android.synthetic.main.activity_story_detail.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class StoryDetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_STORY_ID = "story_id"
        const val FAVORITE_STORY = "favorite_story"

        const val STORY_REQUEST = 30
        const val STORY_RESULT = 49

        fun start(context: Activity, storyId: Int) {
            Intent(context, StoryDetailActivity::class.java).apply {
                putExtra(KEY_STORY_ID, storyId)
            }.let {
                context.startActivityForResult(it, STORY_REQUEST)
            }
        }
    }

    private val commentAdapter: CommentAdapter by lazy {
        CommentAdapter(this, mutableListOf())
    }

    private val storyViewModel: StoryViewModel by viewModel()

    private val preference: StoryPreference by inject()

    private var storyId: Int = 0

    private lateinit var story: Story

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.label_story_detail)

        storyId = intent.getIntExtra(KEY_STORY_ID, 0)

        getStoryDetail()
        observeData()
    }

    private fun getStoryDetail() {
        storyViewModel.getStoryDetail(storyId)
    }

    private fun observeData() {
        storyViewModel.story.observe(this, Observer {
            when (it) {
                is Resource.Loading -> msvStoryDetail.viewState = MultiStateView.ViewState.LOADING
                is Resource.Empty -> msvStoryDetail.viewState = MultiStateView.ViewState.EMPTY
                is Resource.Success -> {
                    msvStoryDetail.viewState = MultiStateView.ViewState.CONTENT
                    showDetail(it.data)
                    storyViewModel.getComments(storyId)
                }
                is Resource.Error -> {
                    msvStoryDetail.viewState = MultiStateView.ViewState.ERROR
                    btnRetry.setOnClickListener {
                        storyViewModel.getTopStories()
                    }
                }
            }
        }
        )

        storyViewModel.comments.observe(this, Observer {
            when (it) {
                is Resource.Loading -> msvComment.viewState = MultiStateView.ViewState.LOADING
                is Resource.Empty -> msvComment.viewState = MultiStateView.ViewState.EMPTY
                is Resource.Success -> {
                    msvComment.viewState = MultiStateView.ViewState.CONTENT
                    showComments(it.data)
                }
                is Resource.Error -> {
                    msvComment.viewState = MultiStateView.ViewState.ERROR
                    btnRetry.setOnClickListener {
                        storyViewModel.getComments(storyId)
                    }
                }
            }
        }
        )
    }

    private fun showDetail(story: Story) {
        this.story = story
        story.apply {
            tvDetailTitle.text = title
            tvBy.text = by
        }
    }

    private fun showComments(comments: List<Comment>) {
        commentAdapter.datas.addAll(comments)
        rvComment.apply {
            layoutManager = LinearLayoutManager(this@StoryDetailActivity)
            adapter = commentAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menuFavorite -> addToFavorite()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addToFavorite() {
        preference.saveString(FAVORITE_STORY, story.title.orEmpty())
        setResult(STORY_RESULT)
        finish()
    }

}
