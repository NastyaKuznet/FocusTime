package com.example.focustime.presentation.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.focustime.R

class FriendsAdapter(private var friends: List<Friend>) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    var friendOrRequest = 0;

    fun GetfriendOrRequest(position: Int): Int {
        friendOrRequest = position
        return friendOrRequest
    }

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendAvatar: ImageView = itemView.findViewById(R.id.friendAvatar)
        val friendNickname: TextView = itemView.findViewById(R.id.friendNickname)
        val friendFocusTime: TextView = itemView.findViewById(R.id.friendFocusTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return if (friendOrRequest == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
            FriendViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_request, parent, false)
            FriendViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]
        //holder.friendAvatar.setImageResource(R.drawable.ic_friend_avatar)
        holder.friendNickname.text = friend.user_nickname
        holder.friendFocusTime.text = friend.user_status
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    fun updateFriends(newFriends: List<Friend>) {
        friends = newFriends
        notifyDataSetChanged()
    }

    fun showTwoFriends() {
        val fakeFriend1 = Friend(1, "Возможный друг1", "Буп")
        val fakeFriend2 = Friend(2, "Возможный друг2", "Тык")
        val updatedFriends = listOf(fakeFriend1, fakeFriend2)
        updateFriends(updatedFriends)
    }
}