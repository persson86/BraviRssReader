package com.mobile.persson.bravirssreader.data

import com.mobile.persson.bravirssreader.data.db.entity.FeedItemEntity
import com.mobile.persson.bravirssreader.data.db.entity.FeedUrlEntity
import com.mobile.persson.bravirssreader.data.model.FeedItem
import io.realm.Realm
import io.realm.RealmResults

class LocalData {
    fun addUrl(url: String) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val feed = realm.createObject(FeedUrlEntity::class.java)
            feed.url = url
        }
    }

    fun getUrls(): RealmResults<FeedUrlEntity>
            = Realm.getDefaultInstance().where(FeedUrlEntity::class.java).findAll()


    fun addFeeds(model: List<FeedItem>, url: String) {
        val realm = Realm.getDefaultInstance()
        val feeds: MutableList<FeedItemEntity> = mutableListOf()

        for (feedResponse in model) {
            val feedItem = FeedItemEntity()
            feedItem.title = feedResponse.title
            feedItem.description = feedResponse.description
            feedItem.link = feedResponse.link
            feedItem.pubDate = feedResponse.pubDate
            feedItem.rss = url
            feeds.add(feedItem)
        }

        realm.beginTransaction()
        realm.insertOrUpdate(feeds)
        realm.commitTransaction()
    }

    fun getRss(): List<FeedItemEntity>
            = Realm.getDefaultInstance().where(FeedItemEntity::class.java).findAll() //TODO .sort("pubDate", Sort.DESCENDING)

}