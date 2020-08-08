package com.employeedetailsapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://10.0.0.18:5000/"

private val interceptor = run {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
}

private val okHttpClient = OkHttpClient.Builder()
    .addNetworkInterceptor(interceptor) // same for .addInterceptor(...)
    .build()

// Using retrofit 2 to pull data from Network

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

object EmployeeApiService {
    val retrofitService: EmployeeApiEndPoint by lazy {
        retrofit.create(EmployeeApiEndPoint::class.java)
    }
}