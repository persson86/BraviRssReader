package com.mobile.persson.bravirssreader.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.mobile.persson.bravirssreader.R
import com.mobile.persson.bravirssreader.data.model.FeedItem


class FeedDetailActivity : AppCompatActivity() {

    companion object {
        private val INTENT_FEED_LINK = "link"

        fun newIntent(context: Context, feed: FeedItem): Intent {
            val intent = Intent(context, FeedDetailActivity::class.java)
            intent.putExtra(INTENT_FEED_LINK, feed.link)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view)
        val link = intent.getStringExtra(INTENT_FEED_LINK)
                ?: throw IllegalStateException("error msg") //TODO

        val myWebView = findViewById(R.id.webview) as WebView
        myWebView.loadUrl(link)
    }
}
