package com.example.focustime.presentation.avatar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.focustime.R

class AvatarAdapter(
    private val avatarImages: IntArray,
    private val onAvatarClick: (Int) -> Unit
) : RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>() {

    private var selectedAvatar:Int? = null


    inner class AvatarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatar_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false)
        return AvatarViewHolder(view)
    }

    override fun getItemCount() = avatarImages.size


    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        val avatarResId = avatarImages[position]

        holder.avatarImageView.setImageResource(avatarResId)
        holder.avatarImageView.alpha = if(selectedAvatar == avatarResId) 0.7f else 1f
        holder.avatarImageView.scaleX = if(selectedAvatar == avatarResId) 1.2f else 1f
        holder.avatarImageView.scaleY = if(selectedAvatar == avatarResId) 1.2f else 1f


        holder.avatarImageView.setOnClickListener {
            onAvatarClick(position)
        }
    }

    fun setSelectedAvatar(position:Int){
        selectedAvatar = avatarImages[position]
        notifyDataSetChanged()
    }

}