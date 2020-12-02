package com.myogardener.kotlin_realtime_database_8

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.item_user.view.*

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User?) {
        with(user!!) {
            if(user.name==null){
                itemView.txt_name.text = ""
                itemView.txt_age.text= ""
                itemView.txt_position.text= ""
            }else{
                itemView.txt_name.text = name
                itemView.txt_age.text = age
                itemView.txt_position.text= position
            }

        }
    }
}