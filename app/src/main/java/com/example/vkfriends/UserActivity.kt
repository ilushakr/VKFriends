package com.example.vkfriends

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vkfriends.models.VKUser
import com.example.vkfriends.requests.VKFriendsRequest
import com.example.vkfriends.requests.VKUsersRequest
import com.example.vkfriends.views.FriendAdapter
import com.example.vkfriends.views.TopSpacingItemDecoration
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import kotlinx.android.synthetic.main.activity_user.*
import java.util.*
import kotlin.collections.HashSet
import kotlin.random.Random

class UserActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            VK.logout()
            WelcomeActivity.startFrom(this)
            finish()
        }

        requestUsers()

        requestFriends()
    }

    private fun requestUsers() {
        VK.execute(VKUsersRequest(), object: VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
                if (!isFinishing && result.isNotEmpty()) {
                    val user = result[0]
                    usersName.text = "${user.firstName} ${user.lastName}"

                    val requestOptions = RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)

                    Glide
                        .with(usersImg.context)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(user.photo)
                        .into(usersImg)

                }
            }
            override fun fail(error: Exception) {
                Log.e(TAG, error.toString())
            }
        })
    }

    private fun requestFriends() {
        VK.execute(VKFriendsRequest(), object: VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
                if (!isFinishing && !result.isEmpty()) {
                    initRecyclerView(result)
                }
            }
            override fun fail(error: Exception) {
                Log.e(TAG, error.toString())
            }
        })
    }


    private fun initRecyclerView(friends: List<VKUser>){
        var friendAdapter = FriendAdapter()
        friendAdapter.submitList(friends.take(5))

        recycleView.apply {
            layoutManager = LinearLayoutManager(this@UserActivity)
            val topSpacingDecorator = TopSpacingItemDecoration(20)
            addItemDecoration(topSpacingDecorator)
            adapter = friendAdapter
        }
    }


    companion object {
        private const val TAG = "UserActivity"

        fun startFrom(context: Context) {
            val intent = Intent(context, UserActivity::class.java)
            context.startActivity(intent)
        }
    }

}