package ru.antropit.spblions.api

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.antropit.spblions.api.model.Entity

interface PoisQuery {
    @GET("pois.json")
    fun loadPoisQuery(@Query("name") name: String): Flowable<List<Entity>>
}

