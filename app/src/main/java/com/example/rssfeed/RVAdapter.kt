package com.example.rssfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rssfeed.databinding.ItemRowBinding

class RVAdapter(val feedList : ArrayList<FeedData>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    class ItemViewHolder(val Binding: ItemRowBinding) : RecyclerView.ViewHolder(Binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.Binding.apply {

            rank.text = feedList[position].rank.toString()
            titlefeed.text = feedList[position].title
        }
    }

    override fun getItemCount(): Int {
        return feedList.size
    }
}