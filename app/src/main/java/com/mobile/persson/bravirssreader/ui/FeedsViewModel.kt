package com.mobile.persson.bravirssreader.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.mobile.persson.bravirssreader.data.db.entity.FeedUrlEntity
import com.mobile.persson.bravirssreader.data.model.Feed
import com.mobile.persson.bravirssreader.repository.FeedRepository

open class FeedsViewModel(application: Application?) : AndroidViewModel(application) {

    private val feedLiveData = MutableLiveData<String>()
    private val repository = FeedRepository()

    val resultLiveData = FeedLiveData().apply {
        this.addSource(feedLiveData) { it?.let { this.feedUrl = it } }
    }

    val isLoadingLiveData = MediatorLiveData<Boolean>().apply {
        this.addSource(resultLiveData) { this.value = false }
    }

    val throwableLiveData = MediatorLiveData<Throwable>().apply {
        this.addSource(resultLiveData) { it?.second?.let { this.value = it } }
    }

    val feedsLiveData = MediatorLiveData<Feed>().apply {
        this.addSource(resultLiveData) { it?.first?.let { this.value = it } }
    }

    fun getFeed(feedUrl: String) {
        feedLiveData.value = feedUrl
    }

    fun addUrl(url: String) {
        repository.addUrl(url)
    }

    fun getUrls(): List<FeedUrlEntity>
            = repository.getUrls()
}
