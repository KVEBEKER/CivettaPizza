package ru.kvebekshaev.civettapizza.data.db.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://n8ls4hdx-5000.euw.devtunnels.ms"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userApiService: ApiService = retrofit.create(ApiService::class.java)
}