package com.example.focustime.presentation.createTypeIndicator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.focustime.data.models.TypeIndicator
import com.example.focustime.databinding.ItemTypeIndicatorBinding

class TypeIndicatorsAdapter(
    private val openTypeClick: (TypeIndicator) -> Unit,
): ListAdapter<TypeIndicator, TypeIndicatorsAdapter.TypeIndicatorsViewHolder>(TypeIndicatorsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeIndicatorsViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemTypeIndicatorBinding.inflate(inflater, parent, false)
        return TypeIndicatorsViewHolder(binding, openTypeClick)
    }

    override fun onBindViewHolder(holder: TypeIndicatorsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TypeIndicatorsViewHolder(
        private val binding: ItemTypeIndicatorBinding,
        private val openTypeClick: (TypeIndicator) -> Unit,
    ):RecyclerView.ViewHolder(binding.root){

        fun bind(typeIndicator: TypeIndicator){
            with(binding){
                nameTypeIndicator.text = typeIndicator.name
                root.setOnClickListener{
                    openTypeClick(typeIndicator)
                }
            }
        }
    }

    private class TypeIndicatorsDiffUtil: DiffUtil.ItemCallback<TypeIndicator>(){
        override fun areItemsTheSame(oldItem: TypeIndicator, newItem: TypeIndicator): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TypeIndicator, newItem: TypeIndicator): Boolean =
            oldItem == newItem

    }
}