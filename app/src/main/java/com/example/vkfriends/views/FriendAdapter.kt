package com.example.vkfriends.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vkfriends.models.VKUser
import com.example.vkfriends.R
import kotlinx.android.synthetic.main.item_friend.view.*
import kotlin.collections.ArrayList


class FriendAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private var items: List<VKUser> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is BlogViewHolder -> {
                holder.bind(items[position])
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(blogList: List<VKUser>){
        items = blogList
    }

    class BlogViewHolder
    constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        private val tabImg = itemView.tab_image
        private val tabName = itemView.tab_name
        private val tabLastName = itemView.tab_last_name

        fun bind(vkUser: VKUser){

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(vkUser.photo)
                .into(tabImg)
            tabName.text = vkUser.firstName
            tabLastName.text = vkUser.lastName

        }

    }

}