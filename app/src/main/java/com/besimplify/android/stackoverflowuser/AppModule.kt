package com.besimplify.android.stackoverflowuser

import android.app.Application
import com.besimplify.android.stackoverflowuser.extensions.isConnected
import com.besimplify.android.stackoverflowuser.network.StackOverflowService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import vn.tiki.android.di.Module

class AppModule(application: Application) : Module() {

  init {
    provideSingleton<StackOverflowService> {
      val cacheSize = (5 * 1024 * 1024).toLong()
      val cache = Cache(application.cacheDir, cacheSize)

      // Source https://medium.com/mindorks/caching-with-retrofit-store-responses-offline-71439ed32fda
      val okHttpClient = OkHttpClient.Builder()
        // Specify the cache we created earlier.
        .cache(cache)
        // Add an Interceptor to the OkHttpClient.
        .addInterceptor { chain ->

          // Get the request from the chain.
          var request = chain.request()

          /**  Leveraging the advantage of using Kotlin,
           *  we initialize the request and change its header depending on whether
           *  the device is connected to Internet or not.
           */
          request = if (application.isConnected())
          /**  If there is Internet, get the cache that was stored 5 seconds ago.
           *  If the cache is older than 5 seconds, then discard it,
           *  and indicate an error in fetching the response.
           *  The 'max-age' attribute is responsible for this behavior.
           */
            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
          else
          /** If there is no Internet, get the cache that was stored 7 days ago.
           *  If the cache is older than 7 days, then discard it,
           *  and indicate an error in fetching the response.
           *  The 'max-stale' attribute is responsible for this behavior.
           *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
           */
            request.newBuilder().header(
              "Cache-Control",
              "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
            ).build()
          // End of if-else statement

          // Add the modified request to the chain.
          chain.proceed(request)
        }
        .build()

      val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
      val retrofit = Retrofit.Builder()
        .baseUrl("https://api.stackexchange.com/2.2/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
      retrofit.create(StackOverflowService::class.java)
    }
  }
}
