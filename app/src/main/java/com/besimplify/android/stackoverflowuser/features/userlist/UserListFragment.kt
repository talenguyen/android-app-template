package com.besimplify.android.stackoverflowuser.features.userlist

import com.besimplify.android.stackoverflowuser.features.core.BaseFragment
import com.besimplify.android.stackoverflowuser.features.core.simpleController
import com.besimplify.android.stackoverflowuser.views.loadingRow

class UserListFragment : BaseFragment() {
  override fun epoxyController() = simpleController {
    loadingRow { id("loader") }
  }
}
