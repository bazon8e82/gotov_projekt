package com.example.aseefm.networking

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL_LASTFM = "https://ws.audioscrobbler.com/2.0/"
const val BASE_URL_MY_API = "https://10.20.35.37:8000/"

class Network {

    private val lastfmService: LastFMService
    private val sofaService: MyApiService

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        val httpClient = OkHttpClient.Builder().addInterceptor(interceptor)

        val retrofitLastFM =
            Retrofit.Builder().baseUrl(BASE_URL_LASTFM)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()).build()

        val retrofitMyApi =
            Retrofit.Builder().baseUrl(BASE_URL_MY_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    httpClient
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS).build()
                ).build()

        lastfmService = retrofitLastFM.create(LastFMService::class.java)
        sofaService = retrofitMyApi.create(MyApiService::class.java)
    }

    fun getLastFMService(): LastFMService = lastfmService

    fun getMyApiService(): MyApiService = sofaService
}