package com.example.focustime.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.focustime.data.models.Indicator
import com.example.focustime.databinding.ItemIndicatorBinding

class IndicatorsAdapter(

): ListAdapter<Indicator, IndicatorsAdapter.IndicatorsViewHolder>(IndicatorsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorsViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemIndicatorBinding.inflate(inflater, parent, false)
        return IndicatorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndicatorsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class IndicatorsViewHolder(
        private val binding: ItemIndicatorBinding,
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(indicator: Indicator){
            with(binding){
                interval.text = indicator.interval.toString()
                date.text = indicator.day
            }
        }
    }

    private class IndicatorsDiffUtil: DiffUtil.ItemCallback<Indicator>(){
        override fun areItemsTheSame(oldItem: Indicator, newItem: Indicator): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Indicator, newItem: Indicator): Boolean =
            oldItem == newItem

    }
}