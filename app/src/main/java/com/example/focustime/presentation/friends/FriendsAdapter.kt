package com.example.focustime.presentation.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.focustime.R

class FriendsAdapter(
    private var friends: List<Friend>,
    private val accountFriend: (Int) -> Unit
) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]
        //holder.friendAvatar.setImageResource(R.drawable.ic_friend_avatar)
        holder.friendNickname.text = friend.user_nickname
        holder.friendFocusTime.text = friend.user_status

        holder.itemView.setOnClickListener{
            accountFriend(friend.user_id)
        }
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    fun updateFriends(newFriends: List<Friend>) {
        friends = newFriends
        notifyDataSetChanged()
    }
}