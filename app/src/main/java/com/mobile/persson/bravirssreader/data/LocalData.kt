package com.mobile.persson.bravirssreader.data

import com.mobile.persson.bravirssreader.data.db.entity.FeedUrlEntity
import io.realm.Realm
import io.realm.RealmResults


class LocalData {

    fun addUrl(url: String) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val feed = realm.createObject(FeedUrlEntity::class.java)
            feed.url = url
            //feed.id = (realm.where(FeedUrlEntity::class.java).max("id") as Int)
        }
    }

    fun getUrls(): RealmResults<FeedUrlEntity> {
        return Realm.getDefaultInstance().where(FeedUrlEntity::class.java).findAll()
    }
}