package com.tsymbaliuk.soundrecognation

import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://api.audd.io/"

    private val authInterceptor = invoke {chain->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_token", Constants.auddApiToken)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val auddClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    private fun getRetrofit() : Retrofit = Retrofit.Builder()
        .client(auddClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    val auddApi : AuddApi = getRetrofit().create(AuddApi::class.java)

}