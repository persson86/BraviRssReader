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


    fun addFeeds(model: List<FeedItem>) {
        val feeds: MutableList<FeedItemEntity> = mutableListOf()
        for (feedReponse in model) {
            val feedItem = FeedItemEntity()
            feedItem.title = feedReponse.title
            feedItem.description = feedReponse.description
            feedItem.link = feedReponse.link
            feedItem.pubDate = feedReponse.pubDate
            feeds.add(feedItem)
        }

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealm(feeds)
        realm.commitTransaction()
    }

    fun getRss(url: String): RealmResults<FeedItemEntity>
            = Realm.getDefaultInstance().where(FeedItemEntity::class.java).findAll()


}