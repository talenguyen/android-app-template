package com.besimplify.android.stackoverflowuser.features.reputationhistorylist

import android.os.Bundle
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.args
import com.airbnb.mvrx.fragmentViewModel
import com.besimplify.android.stackoverflowuser.core.ListFragment
import com.besimplify.android.stackoverflowuser.models.ReputationHistory
import com.besimplify.android.stackoverflowuser.models.User
import vn.tiki.android.di.TikiDi

class ReputationHistoryListFragment : ListFragment<ReputationHistory>() {

  override val viewModel: ReputationHistoryListViewModel by fragmentViewModel()

  private val user by args<User>()

  override fun onCreate(savedInstanceState: Bundle?) {
    TikiDi.openScope(ReputationHistoryListModule(user))
    super.onCreate(savedInstanceState)
  }

  override fun onDestroy() {
    super.onDestroy()
    TikiDi.closeScope()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    toolbar.title = user.name
  }

  override fun EpoxyController.renderItem(item: ReputationHistory) {
  }
}
