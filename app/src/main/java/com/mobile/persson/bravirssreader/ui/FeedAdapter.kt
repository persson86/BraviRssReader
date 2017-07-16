package com.mobile.persson.bravirssreader.ui

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.persson.bravirssreader.R
import com.mobile.persson.bravirssreader.data.model.FeedItem
import kotlinx.android.synthetic.main.view_item.view.*


class FeedAdapter(context: Context) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var feeds: List<FeedItem> = ArrayList()
    private var context: Context? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feeds[position], position)
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent?.context).inflate(R.layout.view_item, parent, false))
        context = context
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

            if (item.rss != "")
                tvRss.text = item.rss

            itemView.setOnClickListener {
                val intent = Intent(context, FeedDetailActivity::class.java)
                intent.putExtra("link", item.link)
                context.startActivity(intent)
            }
        }
    }
}
