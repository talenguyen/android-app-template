package com.besimplify.android.stackoverflowuser

import android.app.Application
import vn.tiki.android.di.TikiDi

class MainApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    TikiDi.openScope(AppModule())
  }

  override fun onTerminate() {
    super.onTerminate()
    TikiDi.closeScope()
  }
}
