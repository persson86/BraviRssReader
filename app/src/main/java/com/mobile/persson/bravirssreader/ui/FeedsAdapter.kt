package com.mobile.persson.bravirssreader.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mobile.persson.bravirssreader.R
import com.mobile.persson.bravirssreader.data.model.FeedItem
import kotlinx.android.synthetic.main.view_item.view.*

class FeedsAdapter : RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {

    private var feeds: List<FeedItem> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feeds[position], position)
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.view_item, parent, false))
        return ViewHolder(root)
    }

    fun addFeeds(feeds: List<FeedItem>?) {
        if (feeds != null) {
            this.feeds = feeds
            notifyDataSetChanged()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: FeedItem, position: Int) = with(itemView) {
            tvTitle.text = item.title
            tvDate.text = item.pubDate
            tvDesc.text = item.description

            itemView.setOnClickListener { Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show() }
        }
    }
}
