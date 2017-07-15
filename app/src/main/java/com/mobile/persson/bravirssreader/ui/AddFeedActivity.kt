package com.mobile.persson.bravirssreader.ui

import android.os.Bundle
import com.mobile.persson.bravirssreader.R
import com.mobile.persson.bravirssreader.base.BaseLifecycleActivity
import kotlinx.android.synthetic.main.activity_add_feed.*

class AddFeedActivity : BaseLifecycleActivity<FeedsViewModel>() {

    override val viewModelClass = FeedsViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_feed)

        button.setOnClickListener { view ->
            viewModel.setUrl(etUrl.text.toString())
        }
    }
}
