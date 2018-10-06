package com.besimplify.android.stackoverflowuser.features.reputationhistorylist

import com.besimplify.android.stackoverflowuser.models.User
import vn.tiki.android.di.Module

class ReputationHistoryListModule(user: User) : Module() {
  init {
    provide { user }
  }
}
