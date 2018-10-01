package com.besimplify.android.stackoverflowuser.features.userdetail

import com.besimplify.android.stackoverflowuser.core.BaseFragment
import com.besimplify.android.stackoverflowuser.core.simpleController
import com.besimplify.android.stackoverflowuser.views.loadingRow

class UserDetailFragment : BaseFragment() {
  override fun epoxyController() = simpleController {
    loadingRow { id("loader") }
  }
}
