package com.mobile.persson.bravirssreader.data

import com.mobile.persson.bravirssreader.data.model.Feed
import io.reactivex.Single
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface RemoteData {
    @GET()
    fun getRss(@Url url: String): Single<Feed>

    companion object Factory {
        fun create(): RemoteData {
            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .baseUrl("http://www.bravi.com.br/")
                    .build()

            return retrofit.create(RemoteData::class.java)
        }
    }
}
