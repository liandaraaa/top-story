package com.lianda.topstoryapp.ui.groupie

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.lianda.topstoryapp.R
import com.lianda.topstoryapp.data.model.Story
import com.lianda.topstoryapp.ui.adapter.StoryAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_content.*

class StoryContentSection (
    val context:Context,
    val datas:List<Story>
): Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder){

            val storyAdapter = StoryAdapter(context, datas)

            rvContent.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = storyAdapter
            }

        }
    }

    override fun getLayout(): Int = R.layout.item_content
}