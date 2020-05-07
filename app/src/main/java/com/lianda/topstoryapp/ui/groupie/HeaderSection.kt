package com.lianda.topstoryapp.ui.groupie

import com.lianda.topstoryapp.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_header.*

class HeaderSection (
    val header:String
): Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder){
            tvHeader.text = header
        }
    }

    override fun getLayout(): Int = R.layout.item_header

}