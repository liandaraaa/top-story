package com.lianda.topstoryapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lianda.topstoryapp.R
import com.lianda.topstoryapp.data.model.Story
import kotlinx.android.synthetic.main.item_story.view.*

class StoryAdapter (val context: Context, val datas:List<Story>, val onItemClick:((id:Int)->Unit)?):
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_story, parent, false))
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class StoryViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(story:Story){
            with(itemView){
                tvTitle.text = story.title
                tvScore.text = String.format(context.getString(R.string.format_total_score), story.score)
                tvTotalComment.text = String.format(context.getString(R.string.format_total_commentar), story.kids?.size)

                setOnClickListener {
                    onItemClick?.invoke(story.id ?: 0)
                }
            }
        }
    }
}