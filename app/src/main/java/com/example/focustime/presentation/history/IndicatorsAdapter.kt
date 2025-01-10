package com.example.focustime.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.focustime.data.models.Indicator
import com.example.focustime.databinding.ItemIndicatorBinding
import java.util.concurrent.TimeUnit

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
                interval.text = formatTime(indicator.interval.toLong())
                date.text = indicator.day
            }
        }

        private fun formatTime(seconds: Long): String {
            val minutes = TimeUnit.SECONDS.toMinutes(seconds)
            val remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes)
            val hours = TimeUnit.MINUTES.toHours(minutes)
            val remainingMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
            return String.format("%02d:%02d:%02d", hours, remainingMinutes, remainingSeconds)
        }
    }

    private class IndicatorsDiffUtil: DiffUtil.ItemCallback<Indicator>(){
        override fun areItemsTheSame(oldItem: Indicator, newItem: Indicator): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Indicator, newItem: Indicator): Boolean =
            oldItem == newItem

    }
}