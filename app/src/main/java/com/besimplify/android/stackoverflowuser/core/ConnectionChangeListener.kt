package com.besimplify.android.stackoverflowuser.core

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.besimplify.android.stackoverflowuser.extensions.isConnected

class ConnectionChangeListener(
  lifecycle: Lifecycle,
  private val context: Context,
  private val callback: (Boolean) -> Unit
) : LifecycleObserver {

  private val broadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      callback.invoke(context?.isConnected() ?: false)
    }
  }

  init {
    lifecycle.addObserver(this)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun start() {
    context.registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun stop() {
    context.unregisterReceiver(broadcastReceiver)
  }
}
