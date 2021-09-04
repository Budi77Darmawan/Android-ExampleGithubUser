package com.bd_drmwan.example

import com.bd_drmwan.example.BuildConfig.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private var retrofit: Retrofit? = null

    fun getApiClient() : Retrofit? {
        /**
         * has the same function as the code below
         */
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