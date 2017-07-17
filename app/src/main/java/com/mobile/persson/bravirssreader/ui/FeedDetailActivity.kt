package com.mobile.persson.bravirssreader.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import com.mobile.persson.bravirssreader.R
import com.mobile.persson.bravirssreader.data.model.FeedItem
import kotlinx.android.synthetic.main.fragment_toolbar.*


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
        setContentView(R.layout.activity_feed_detail)

        loadToolbar()

        val link = intent.getStringExtra(INTENT_FEED_LINK)
                ?: throw IllegalStateException("error msg") //TODO

        val myWebView = findViewById(R.id.webview) as WebView
        myWebView.loadUrl(link)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        tvToolbarTitle.text = "Feed Detail"
        ivIcon.visibility = View.GONE
    }
}
