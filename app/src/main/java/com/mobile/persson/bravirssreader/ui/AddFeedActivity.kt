package com.mobile.persson.bravirssreader.ui

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.mobile.persson.bravirssreader.R
import com.mobile.persson.bravirssreader.base.BaseLifecycleActivity
import kotlinx.android.synthetic.main.activity_add_feed.*
import kotlinx.android.synthetic.main.fragment_toolbar.*

class AddFeedActivity : BaseLifecycleActivity<FeedViewModel>() {

    override val viewModelClass = FeedViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_feed)

        loadToolbar()

        button.setOnClickListener { view ->
            var feedUrl = getString(R.string.string_http)
            feedUrl = feedUrl.plus(etUrl.text.toString())

            viewModel.addUrl(feedUrl)
            finish()
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
        }
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

        tvToolbarTitle.text = getText(R.string.string_add_new_feed)
        ivIcon.visibility = View.GONE
    }
}
