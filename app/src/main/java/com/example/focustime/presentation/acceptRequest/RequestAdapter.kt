package com.example.focustime.presentation.acceptRequest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.focustime.R
import com.example.focustime.presentation.friends.Friend

class RequestAdapter(
    private var friends: List<Friend>,
    private val onAddFriendClicked: (Friend, (Boolean) -> Unit) -> Unit,
    private val accountFriend: (Int) -> Unit
) : RecyclerView.Adapter<RequestAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val friendAvatar: ImageView = itemView.findViewById(R.id.friendAvatar)
        val friendNickname: TextView = itemView.findViewById(R.id.friendNickname)
        val friendFocusTime: TextView = itemView.findViewById(R.id.friendFocusTime)
        val addFriendButton: Button = itemView.findViewById(R.id.addFriendButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_request, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]
        holder.friendAvatar.setImageResource(setUpAvatar(friend.avatar_id))
        holder.friendNickname.text = friend.user_nickname
        holder.friendFocusTime.text = friend.user_status

        holder.addFriendButton.setOnClickListener {
            onAddFriendClicked(friend) { result ->
                if (result) {
                    holder.addFriendButton.text = "✓"
                    holder.addFriendButton.isEnabled = false
                }
            }
        }

        holder.itemView.setOnClickListener{
            accountFriend(friend.user_id)
        }
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    private fun setUpAvatar(avatarId: Int): Int{
        val avatarResId = when (avatarId) {
            0 -> R.drawable.avatar1
            1 -> R.drawable.avatar2
            2 -> R.drawable.avatar3
            3 -> R.drawable.avatar4
            4 -> R.drawable.avatar5
            else -> R.drawable.avatar1
        }
        return avatarResId
    }

    fun updateFriends(newFriends: List<Friend>) {
        friends = newFriends
        notifyDataSetChanged()
    }
}