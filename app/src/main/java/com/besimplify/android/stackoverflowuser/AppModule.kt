package com.besimplify.android.stackoverflowuser

import com.besimplify.android.stackoverflowuser.network.StackOverflowService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import vn.tiki.android.di.Module

class AppModule : Module() {

  init {
    provideSingleton<StackOverflowService> {
      val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
      val retrofit = Retrofit.Builder()
        .baseUrl("https://api.stackexchange.com/2.2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
      retrofit.create(StackOverflowService::class.java)
    }
  }
}
