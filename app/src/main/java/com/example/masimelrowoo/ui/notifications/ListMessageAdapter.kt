package com.example.masimelrowoo.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.masimelrowoo.databinding.ItemRowMessageBinding

class ListMessageAdapter(private val listMessage: ArrayList<Message>) : RecyclerView.Adapter<ListMessageAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowMessageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listMessage[position])
    }

    override fun getItemCount(): Int = listMessage.size

    inner class ListViewHolder(private val binding: ItemRowMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            with(binding) {
                tvItemDate.text = message.date
                tvItemMessage.text = message.message
            }
        }
    }
}