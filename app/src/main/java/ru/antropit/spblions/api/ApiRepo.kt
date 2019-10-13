package ru.antropit.spblions.api

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepo {
    private val API_URL = "https://spb.maris.no/"

    fun createAdapter(): PoisQuery {
        val adapter = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        return adapter.create<PoisQuery>(PoisQuery::class.java)
    }
}