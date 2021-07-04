package com.example.newsapi.apiservice

import com.example.newsapi.util.NullStringAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "c63952b703fa4c2a8ee170bb54a42dfe"

private val moshi = Moshi.Builder()
    .add(NullStringAdapter)
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()