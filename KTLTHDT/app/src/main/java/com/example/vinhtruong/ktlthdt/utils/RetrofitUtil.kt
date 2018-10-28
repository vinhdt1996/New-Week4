package com.example.vinhtruong.ktlthdt.utils

import android.util.Log
import com.example.vinhtruong.ktlthdt.BuildConfig

import com.example.vinhtruong.ktlthdt.api.GoogleApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class RetrofitUtil {

    companion object {

        /**
         * Builder for Google API Service
         * @return A GoogleApiService instance with Retrofit and RxJava
         */
        fun builderGoogleService(): GoogleApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GoogleApiService.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client())
                    .build()
            return retrofit.create(GoogleApiService::class.java)
        }

        /**
         * Builder for set up a OkHttpClient
         * @return A OkHttpClient instance have added ending query with Google Map API key
         */
        private fun client(): OkHttpClient {

            return OkHttpClient.Builder()
                    .addNetworkInterceptor { chain ->
                        var request = chain.request()
                        val url = request.url()
                                .newBuilder()
                                .addQueryParameter("key", "AIzaSyA0s2e-r1rt1xyh7RGdLd4VbWJY55uWgaE")
                                .build()
                        Log.d("URL_KEY", url.toString())
                        request = request.newBuilder()
                                .url(url)
                                .build()
                        chain.proceed(request)
                    }
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
        }
    }

}