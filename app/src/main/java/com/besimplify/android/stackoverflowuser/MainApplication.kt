package com.besimplify.android.stackoverflowuser

import android.app.Application
import com.besimplify.android.stackoverflowuser.network.StackOverflowService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MainApplication : Application() {

  private val stackOverflowServiceModule: Module = applicationContext {
    bean {
      val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
      val retrofit = Retrofit.Builder()
        .baseUrl("https://api.stackexchange.com/2.2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
      retrofit.create(StackOverflowService::class.java)
    }
  }

  override fun onCreate() {
    super.onCreate()
    startKoin(this, listOf(stackOverflowServiceModule))
  }
}
