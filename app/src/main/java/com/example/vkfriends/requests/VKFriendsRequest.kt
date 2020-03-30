package com.example.vkfriends.requests

import com.vk.api.sdk.requests.VKRequest
import com.example.vkfriends.models.VKUser
import org.json.JSONObject
import java.util.ArrayList

class VKFriendsRequest: VKRequest<List<VKUser>>("friends.get") {
    init {
        addParam("fields", "photo_200")
    }

    override fun parse(r: JSONObject): List<VKUser> {
        val users = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKUser>()
        for (i in 0 until users.length()) {
            result.add(VKUser.parse(users.getJSONObject(i)))
        }
        return result
    }
}