package com.mobile.persson.bravirssreader.data.db.entity

import io.realm.RealmObject

open class FeedUrlEntity : RealmObject() {
    open var url: String? = null
}