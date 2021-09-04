package com.bd_drmwan.example

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://api.github.com/"

    fun getApiClient() : Retrofit? {
//        if (retrofit == null) {
//            retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//        return retrofit

        retrofit?.let {
            return it
        } ?: run {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }

    }
}