package com.erfolgi.dissolvedoxygenmonitor.api

import com.erfolgi.dissolvedoxygenmonitor.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    val service: APICall
        get() = retrofit(BuildConfig.BASE_URL)!!.create(APICall::class.java)

    companion object {
        private var retrofit: Retrofit? = null

        fun retrofit(BASE_URL: String): Retrofit? {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            if (retrofit == null) {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build()
            }
            return retrofit
        }
    }
}