package com.mobile.persson.bravirssreader.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import com.mobile.persson.bravirssreader.data.db.entity.FeedUrlEntity
import com.mobile.persson.bravirssreader.data.model.Feed
import com.mobile.persson.bravirssreader.repository.FeedRepository

open class FeedsViewModel(application: Application?) : AndroidViewModel(application) {

    private val repository = FeedRepository()
    val resultLiveData = FeedsLiveData(repository)
    val throwableLiveData = MediatorLiveData<Throwable>()
    val feedsLiveData = MediatorLiveData<Feed>()

    init {
        throwableLiveData.addSource(resultLiveData) {
            it?.second?.let { throwableLiveData.value = it }
        }
    }

    init {
        feedsLiveData.addSource(resultLiveData) {
            it?.first?.let { feedsLiveData.value = it }
        }
    }

    fun setUrl(url: String) {
        repository.addUrl(url)
    }

    fun getUrls(): List<FeedUrlEntity>
            = repository.getUrls()

}