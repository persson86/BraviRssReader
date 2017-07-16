package com.mobile.persson.bravirssreader.repository

import com.mobile.persson.bravirssreader.data.db.entity.FeedUrlEntity
import com.mobile.persson.bravirssreader.data.model.Feed
import io.reactivex.Single

interface RepositoryData {

    fun getRss(url: String): Single<Feed>

    fun addUrl(url: String)

    fun getUrls(): List<FeedUrlEntity>
}