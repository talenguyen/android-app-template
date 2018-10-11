package com.besimplify.android.stackoverflowuser.features.reputationhistorylist

import android.os.Bundle
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.args
import com.airbnb.mvrx.fragmentViewModel
import com.besimplify.android.stackoverflowuser.core.ListFragment
import com.besimplify.android.stackoverflowuser.extensions.formatDate
import com.besimplify.android.stackoverflowuser.extensions.formatTime
import com.besimplify.android.stackoverflowuser.models.ReputationHistory
import com.besimplify.android.stackoverflowuser.models.User
import com.besimplify.android.stackoverflowuser.views.dateRow
import com.besimplify.android.stackoverflowuser.views.reputationHistoryRow
import vn.tiki.android.di.TikiDi
import java.util.Date

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

  override fun EpoxyController.renderList(list: List<ReputationHistory>) {
    list
      .sortedBy { it.creationDate }
      .groupBy { Date(user.lastAccessDate * 1000).formatDate() }
      .entries
      .forEach {
        dateRow {
          id("dateRow.${it.key}")
          dateText(it.key)
        }

        it.value
          .forEachIndexed { index, reputationHistory ->
            reputationHistoryRow {
              id("reputation_history.$index")
              reputationChange(reputationHistory.reputationChange)
              type(reputationHistory.type)
              postId("${reputationHistory.postId}")
              lastAccessDate(Date(reputationHistory.creationDate * 1000).formatTime())
            }
          }
      }
  }
}
