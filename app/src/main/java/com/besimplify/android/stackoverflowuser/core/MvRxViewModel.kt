package com.besimplify.android.stackoverflowuser.core

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.besimplify.android.stackoverflowuser.BuildConfig

abstract class MvRxViewModel<S : MvRxState>(
  initialState: S,
  debugMode: Boolean = BuildConfig.DEBUG
) : BaseMvRxViewModel<S>(initialState, debugMode)
