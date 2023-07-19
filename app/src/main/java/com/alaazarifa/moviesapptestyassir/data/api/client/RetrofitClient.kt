package com.alaazarifa.moviesapptestyassir.data.api.client

import com.alaazarifa.moviesapptestyassir.data.api.API_KEY
import com.alaazarifa.moviesapptestyassir.data.api.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {

    companion object {
        private const val TIME_OUT = 60 * 1000L

        private val RETROFIT_INSTANCE: Retrofit by lazy {
            val okHttpBuilder = OkHttpClient.Builder()


            okHttpBuilder.addInterceptor { chain ->
                val original = chain.request()
                val originalUrl = original.url

                // Adding auth key parameter to all requests
               val url = originalUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                // Adding these headers to all requests
                val requestBuilder = original.newBuilder()
                    .url(url)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")

                val request = requestBuilder.build()
                chain.proceed(request)
            }

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpBuilder.addInterceptor(interceptor)

            okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)

            Retrofit.Builder()
                .client(okHttpBuilder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun <T> getService(clazz: Class<T>): T = RETROFIT_INSTANCE.create(clazz)
    }
}