package com.mobile.persson.bravirssreader.repository

import android.util.Log
import com.mobile.persson.bravirssreader.data.RemoteData
import com.mobile.persson.bravirssreader.data.model.Feed
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FeedRepository : RepositoryData {
    private val remoteData = RemoteData.Factory.create()

    override fun getRss(url: String): Single<Feed> {
        return remoteData
                .getRss(url)
                .doOnSuccess {
                    Log.v("LFSP", "GET OK")
                }
                .doOnError {
                    Log.v("LFSP", "GET NOK")
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
