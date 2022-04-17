package com.example.tatarby

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tatarby.databinding.EventLayoutBinding

class event_adapter: ListAdapter<Event, event_adapter.ItemHolder>(ItemComparator()) {
    class ItemHolder(private val binding: EventLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        var count: TextView = binding.count
        var hochu: TextView = binding.textView6
        var idy: TextView = binding.textView8
        fun bind(event: Event) = with(binding){
            eventCard.text = event.event
            dateCard.text = event.date
            timeCard.text = event.time
            textCard.text = event.text
            count.text = event.count
        }
        companion object{
            fun create(parent: ViewGroup) : ItemHolder{
                return ItemHolder(EventLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }
    class ItemComparator: DiffUtil.ItemCallback<Event>(){
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return  oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.hochu.setOnClickListener {
            val a = holder.count.text.toString().toInt()+1
            holder.count.setText(a.toString())
            holder.idy.visibility = View.VISIBLE
            holder.hochu.visibility = View.GONE
        }
        holder.idy.setOnClickListener {
            val a = holder.count.text.toString().toInt()-1
            holder.count.setText(a.toString())
            holder.idy.visibility = View.GONE
            holder.hochu.visibility = View.VISIBLE
        }
        holder.bind(getItem(position))
    }
}