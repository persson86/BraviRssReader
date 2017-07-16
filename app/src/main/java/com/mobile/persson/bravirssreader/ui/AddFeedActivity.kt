package com.mobile.persson.bravirssreader.ui

import android.os.Bundle
import android.widget.Toast
import com.mobile.persson.bravirssreader.R
import com.mobile.persson.bravirssreader.base.BaseLifecycleActivity
import kotlinx.android.synthetic.main.activity_add_feed.*

class AddFeedActivity : BaseLifecycleActivity<FeedViewModel>() {

    override val viewModelClass = FeedViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_feed)

        button.setOnClickListener { view ->

            var feedUrl: String = "http://"
            feedUrl = feedUrl.plus(etUrl.text.toString())

            viewModel.addUrl(feedUrl)
            Toast.makeText(this, "Feed/ Url add com sucesso", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
