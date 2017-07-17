package com.mobile.persson.bravirssreader.data.db.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class FeedItemEntity : RealmObject() {

    @PrimaryKey
    var title: String? = null
    var pubDate: String? = null
    var link: String? = null
    var description: String? = null
    var rss: String? = null
    var date: Date? = null
}