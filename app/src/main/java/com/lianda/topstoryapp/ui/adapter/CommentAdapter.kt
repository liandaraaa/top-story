package com.lianda.topstoryapp.ui.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lianda.topstoryapp.R
import com.lianda.topstoryapp.data.model.Comment
import com.lianda.topstoryapp.data.model.Story
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_story.view.*
import kotlinx.android.synthetic.main.item_story.view.tvTitle

class CommentAdapter(val context: Context, val datas: MutableList<Comment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_comment,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(comment: Comment) {
            with(itemView) {
                tvTitle.text = comment.by
                tvComment.text = Html.fromHtml(comment.text)
            }
        }
    }
}