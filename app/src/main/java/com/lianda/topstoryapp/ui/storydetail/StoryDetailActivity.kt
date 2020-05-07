package com.lianda.topstoryapp.ui.storydetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import com.lianda.topstoryapp.R
import com.lianda.topstoryapp.data.model.Comment
import com.lianda.topstoryapp.data.model.Story
import com.lianda.topstoryapp.depth.service.model.Resource
import com.lianda.topstoryapp.ui.adapter.CommentAdapter
import com.lianda.topstoryapp.ui.viewmodel.StoryViewModel
import kotlinx.android.synthetic.main.activity_story_detail.*
import kotlinx.android.synthetic.main.activity_story_detail.tvTitle
import kotlinx.android.synthetic.main.layout_error.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel

class StoryDetailActivity : AppCompatActivity() {

    companion object {
        const val KEY_STORY_ID = "story_id"

        fun start(context: Context, storyId: Int) {
            context.startActivity<StoryDetailActivity>(
                KEY_STORY_ID to storyId
            )
        }
    }

    private val commentAdapter: CommentAdapter by lazy {
        CommentAdapter(this, mutableListOf())
    }

    private val storyViewModel: StoryViewModel by viewModel()

    private var storyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)

        storyId = intent.getIntExtra(KEY_STORY_ID, 0)

        getStoryDetail()
        observeData()
    }

    private fun getStoryDetail() {
        storyViewModel.getStoryDetail(storyId)
        storyViewModel.getComments(storyId)
    }

    private fun observeData() {
        storyViewModel.story.observe(this, Observer {
            when (it) {
                is Resource.Loading -> msvStoryDetail.viewState = MultiStateView.ViewState.LOADING
                is Resource.Empty -> msvStoryDetail.viewState = MultiStateView.ViewState.EMPTY
                is Resource.Success -> {
                    msvStoryDetail.viewState = MultiStateView.ViewState.CONTENT
                    showDetail(it.data)
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
                is Resource.Loading -> msvStoryDetail.viewState = MultiStateView.ViewState.LOADING
                is Resource.Empty -> msvStoryDetail.viewState = MultiStateView.ViewState.EMPTY
                is Resource.Success -> {
                    msvStoryDetail.viewState = MultiStateView.ViewState.CONTENT
                    showComments(it.data)
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
    }

    private fun showDetail(story: Story) {
        story.apply {
            tvTitle.text = title
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
}
