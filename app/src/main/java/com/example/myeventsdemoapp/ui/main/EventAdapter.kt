package com.example.myeventsdemoapp.ui.main

import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myeventsdemoapp.R
import com.example.myeventsdemoapp.data.local.entity.Event
import com.example.myeventsdemoapp.databinding.ItemEventBinding
import com.example.myeventsdemoapp.utils.formatTime

class EventAdapter(
    private val onBookmark: (Event,Boolean) -> Unit,
    private val onClick: (Event) -> Unit
) : ListAdapter<Event, EventAdapter.VH>(Diff()) {

    inner class VH(val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var bookmarkedIds = emptyList<String>()
    private var lastClickTime = 0L
    private var lastClickTime1 = 0L
    var clickCount=0

    fun setBookmarkedIds(ids: List<String>) {
        bookmarkedIds = ids
        notifyDataSetChanged()
    }

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

            // ✅ Format time
            time.text = formatTime(item.time)

            // ✅ Distance (assignment requirement)
            distance.text = "${item.distance} km"

            // ✅ Image loading (optimized)
            Glide.with(image.context)
                .load(item.imageUrl)
                .placeholder(android.R.color.darker_gray)
                .error(android.R.color.holo_red_dark)
                .centerCrop()
                .into(image)

//            // ✅ Bookmark state UI

            val isBookmarked = bookmarkedIds.contains(item.id)
            if (isBookmarked) {

                bookmark.text="Remove Bookmark"
            } else {
                bookmark.text="Bookmark"
            }
//            bookmark.text = if (item.isBookmarked) {
//                "Remove Bookmark"
//            } else {
//                "Bookmark"
//            }

            bookmark.setOnClickListener {
                val currentTime = SystemClock.elapsedRealtime()
                if (currentTime - lastClickTime > 500) { // 500ms debounce
                    lastClickTime = currentTime

                   // bookmark.text="Remove Bookmark"
                    onBookmark(item,isBookmarked)
                    //setBookmarkedIds(listOf(item.id))

                }

            }

            root.setOnClickListener {
                val currentTime = SystemClock.elapsedRealtime()
                if (currentTime - lastClickTime1 > 500) { // 500ms debounce
                    lastClickTime1 = currentTime
                    onClick(item)
                }

            }
        }
    }

    // ✅ Efficient updates
    class Diff : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(old: Event, new: Event) =
            old.id == new.id

        override fun areContentsTheSame(old: Event, new: Event) =
            old == new
    }
}
