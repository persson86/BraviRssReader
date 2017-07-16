package com.mobile.persson.bravirssreader.repository

import android.util.Log
import com.mobile.persson.bravirssreader.data.LocalData
import com.mobile.persson.bravirssreader.data.RemoteData
import com.mobile.persson.bravirssreader.data.db.entity.FeedUrlEntity
import com.mobile.persson.bravirssreader.data.model.Feed
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers


class FeedRepository : RepositoryData {
    private val remoteData = RemoteData.Factory.create()
    private val localData = LocalData()

    override fun getRss(url: String): Single<Feed>
            = remoteData
            .getRss(url)
            .doOnSuccess {
                Log.v("LFSP", "GET OK")
                localData.addFeeds(it.channel?.feedItems!!, url)
            }
            .doOnError {
                Log.v("LFSP", "GET NOK")
            }
            .subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun addUrl(url: String) {
        if (!url.isEmpty()) {
            localData.addUrl(url)
        }
    }

    override fun getUrls(): List<FeedUrlEntity>
            = localData.getUrls()
}
