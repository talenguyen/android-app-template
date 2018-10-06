package com.besimplify.android.stackoverflowuser.core

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionChangeListener(
  lifecycle: Lifecycle,
  private val context: Context,
  private val callback: (Boolean) -> Unit
) : LifecycleObserver {

  private val broadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
      val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
      callback.invoke(isConnected)
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
