package com.besimplify.android.stackoverflowuser.features.userlist

import android.support.v4.app.FragmentActivity
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.besimplify.android.stackoverflowuser.BuildConfig
import com.besimplify.android.stackoverflowuser.core.MvRxViewModel
import com.besimplify.android.stackoverflowuser.models.ListResponse
import com.besimplify.android.stackoverflowuser.models.User
import com.besimplify.android.stackoverflowuser.network.StackOverflowService
import org.koin.android.ext.android.inject

data class UserListState(
  val page: Int = 0,
  val users: List<User> = emptyList(),
  val usersRequest: Async<ListResponse<User>> = Uninitialized
) : MvRxState

open class UserListViewModel(
  initialState: UserListState,
  private val stackOverflowService: StackOverflowService,
  debugMode: Boolean = BuildConfig.DEBUG
) : MvRxViewModel<UserListState>(initialState, debugMode) {

  init {
    fetchNextPage()
  }

  fun fetchNextPage() = withState { state ->
    if (state.usersRequest is Loading) return@withState

    val nextPage = state.page + 1
    stackOverflowService.users(page = nextPage).execute {
      copy(
        page = if (it is Success) nextPage else page,
        users = users + (it()?.items ?: emptyList()),
        usersRequest = it
      )
    }
  }

  companion object : MvRxViewModelFactory<UserListState> {
    @JvmStatic override fun create(activity: FragmentActivity, state: UserListState): BaseMvRxViewModel<UserListState> {
      val stackOverflowService: StackOverflowService by activity.inject()
      return UserListViewModel(state, stackOverflowService)
    }
  }
}