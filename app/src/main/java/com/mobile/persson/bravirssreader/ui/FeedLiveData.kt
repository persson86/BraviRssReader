package com.mobile.persson.bravirssreader.ui

import android.arch.lifecycle.MediatorLiveData
import com.mobile.persson.bravirssreader.data.model.Feed
import com.mobile.persson.bravirssreader.repository.FeedRepository
import io.reactivex.disposables.Disposable


class FeedLiveData : MediatorLiveData<Pair<Feed?, Throwable?>>() {

    private var disposable: Disposable? = null

    var feedUrl: String? = null
        set(value) {
            value?.let {
                disposable = FeedRepository()
                        .getRss(it)
                        .subscribe { data, error -> this@FeedLiveData.value = Pair(data, error) }
            }
        }

    override fun onInactive() {
        super.onInactive()
        if (disposable?.isDisposed?.not() ?: false) {
            disposable?.dispose()
        }
    }

}