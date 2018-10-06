package com.besimplify.android.stackoverflowuser.features.userlist

import android.support.v4.app.FragmentActivity
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.besimplify.android.stackoverflowuser.BuildConfig
import com.besimplify.android.stackoverflowuser.core.ListState
import com.besimplify.android.stackoverflowuser.core.ListViewModel
import com.besimplify.android.stackoverflowuser.models.ListResponse
import com.besimplify.android.stackoverflowuser.models.User
import com.besimplify.android.stackoverflowuser.network.StackOverflowService
import io.reactivex.Observable
import org.koin.android.ext.android.inject

class UserListViewModel(
  initialState: ListState<User>,
  private val stackOverflowService: StackOverflowService,
  debugMode: Boolean = BuildConfig.DEBUG
) : ListViewModel<User>(initialState, debugMode) {

  init {
    fetchNextPage()
  }

  override fun fetchList(page: Int): Observable<ListResponse<User>> = stackOverflowService.users(page = page)

  companion object : MvRxViewModelFactory<ListState<User>> {
    @JvmStatic
    override fun create(activity: FragmentActivity, state: ListState<User>): BaseMvRxViewModel<ListState<User>> {
      val stackOverflowService: StackOverflowService by activity.inject()
      return UserListViewModel(state, stackOverflowService)
    }
  }
}
