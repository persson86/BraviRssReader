package com.mobile.persson.bravirssreader.data.db.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FeedItemEntity : RealmObject() {

    var title: String? = null

    var pubDate: String? = null
    var link: String? = null
    var description: String? = null
    var rss: String? = null
}