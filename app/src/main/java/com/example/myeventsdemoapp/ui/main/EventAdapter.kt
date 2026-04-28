package com.example.myeventsdemoapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myeventsdemoapp.data.local.entity.Event
import com.example.myeventsdemoapp.databinding.ItemEventBinding
import com.example.myeventsdemoapp.utils.formatTime

class EventAdapter(
    private val onBookmark: (Event) -> Unit,
    private val onClick: (Event) -> Unit
) : ListAdapter<Event, EventAdapter.VH>(Diff()) {

    inner class VH(val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)

        with(holder.binding) {

            title.text = item.title


            time.text = formatTime(item.time)


            distance.text = String.format("%.2f km", item.distance)


            Glide.with(image.context)
                .load(item.imageUrl)
                .placeholder(android.R.color.darker_gray)
                .error(android.R.color.holo_red_dark)
                .centerCrop()
                .into(image)



            bookmark.text = if (item.isBookmarked) {
                "Remove Bookmark"
            } else {
                "Bookmark"
            }

            bookmark.setOnClickListener {
                it.isEnabled = false
                val updatedItem = item.copy(
                    isBookmarked = !item.isBookmarked
                )
                val newList = currentList.toMutableList()
                newList[holder.adapterPosition] = updatedItem
                submitList(newList)
                onBookmark(updatedItem)
                it.postDelayed({ it.isEnabled = true }, 500)

            }

            root.setOnClickListener {
                it.isEnabled = false
                onClick(item)
                it.postDelayed({ it.isEnabled = true }, 500)

            }
        }
    }


    class Diff : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(old: Event, new: Event) =
            old.id == new.id

        override fun areContentsTheSame(old: Event, new: Event) =
            old == new
    }
}
