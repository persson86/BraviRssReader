package com.mobile.persson.bravirssreader.ui

import android.arch.lifecycle.MediatorLiveData
import com.mobile.persson.bravirssreader.data.model.Feed
import com.mobile.persson.bravirssreader.repository.FeedRepository
import io.reactivex.disposables.Disposable


class FeedsLiveData(repository: FeedRepository) : MediatorLiveData<Pair<Feed?, Throwable?>>() {

    private var disposable: Disposable? = null

    init {
        disposable = repository
                .getRss("http://feed.androidauthority.com/")
                .subscribe { data, error -> this@FeedsLiveData.value = Pair(data, error) }
    }


    override fun onInactive() {
        super.onInactive()
        if (disposable?.isDisposed?.not() ?: false) {
            disposable?.dispose()
        }
    }

}
