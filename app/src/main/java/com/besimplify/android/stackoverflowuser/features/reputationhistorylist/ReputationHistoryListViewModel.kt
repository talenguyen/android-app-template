package com.besimplify.android.stackoverflowuser.features.reputationhistorylist

import android.support.v4.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.besimplify.android.stackoverflowuser.BuildConfig
import com.besimplify.android.stackoverflowuser.core.ListState
import com.besimplify.android.stackoverflowuser.core.ListViewModel
import com.besimplify.android.stackoverflowuser.models.ListResponse
import com.besimplify.android.stackoverflowuser.models.ReputationHistory
import com.besimplify.android.stackoverflowuser.models.User
import com.besimplify.android.stackoverflowuser.network.StackOverflowService
import io.reactivex.Observable
import vn.tiki.android.di.TikiDi

class ReputationHistoryListViewModel(
  initialState: ListState<ReputationHistory>,
  private val stackOverflowService: StackOverflowService,
  private val userId: Long,
  debugMode: Boolean = BuildConfig.DEBUG
) : ListViewModel<ReputationHistory>(initialState, debugMode) {

  override fun fetchList(page: Int): Observable<ListResponse<ReputationHistory>> {
    return stackOverflowService.reputationHistory(userId = userId, page = page)
  }

  companion object : MvRxViewModelFactory<ListState<ReputationHistory>> {
    @JvmStatic
    override fun create(
      activity: FragmentActivity,
      state: ListState<ReputationHistory>
    ): BaseMvRxViewModel<ListState<ReputationHistory>> {
      val stackOverflowService: StackOverflowService by TikiDi.inject()
      val user: User by TikiDi.inject()
      return ReputationHistoryListViewModel(state, stackOverflowService, user.id)
    }
  }
}
