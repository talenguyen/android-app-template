package com.besimplify.android.stackoverflowuser.features.userlist

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.withState
import com.besimplify.android.stackoverflowuser.models.ListResponse
import com.besimplify.android.stackoverflowuser.models.User
import com.besimplify.android.stackoverflowuser.network.StackOverflowService
import com.besimplify.android.stackoverflowuser.util.RxSchedulersOverrideRule
import com.besimplify.android.stackoverflowuser.util.mockUserListResponse
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test

class UserListViewModelTest {
  // A rule that makes all subscriptions to be subscribed and observed on a trampoline scheduler
  @get:Rule
  val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

  private val stackOverflowService: StackOverflowService = mock()
  private val mockUsers = mockUserListResponse().items
  private val mockUsersPage1 = mockUsers.take(15)
  private val mockUsersPage2 = mockUsers.takeLast(mockUsers.size - 15)

  @Test
  fun fetchFirstPageError() {
    mockError(1)

    val tested = UserListViewModel(UserListState(), stackOverflowService, true)

    withState(tested) {
      assertThat(it.usersRequest is Fail).isTrue()
    }
  }

  @Test
  fun fetchFirstPageSuccess() {
    mockSuccess(1)

    val tested = UserListViewModel(UserListState(), stackOverflowService, true)

    withState(tested) {
      assertThat(it.usersRequest is Success).isTrue()
      assertThat(it.users).isEqualTo(mockUsersPage1)
    }
  }

  @Test
  fun fetchMorePageError() {
    // Mock first page success
    mockSuccess(1)

    // Mock second page error
    mockError(2)

    // First page success
    val tested = UserListViewModel(UserListState(), stackOverflowService, true)

    // Load second page
    tested.fetchNextPage()

    withState(tested) {
      assertThat(it.users).isEqualTo(mockUsersPage1)
      assertThat(it.usersRequest is Fail).isTrue()
    }
  }

  @Test
  fun fetchMorePageSuccess() {
    // Mock first page success
    mockSuccess(1)

    // Mock second page error
    mockSuccess(2)

    // First page success
    val tested = UserListViewModel(UserListState(), stackOverflowService, true)

    // Load second page
    tested.fetchNextPage()

    withState(tested) {
      assertThat(it.users).isEqualTo(mockUsersPage1 + mockUsersPage2)
      assertThat(it.usersRequest is Success).isTrue()
    }
  }

  private fun mockSuccess(page: Int) {
    val response: ListResponse<User> = when (page) {
      1 -> ListResponse(true, mockUsersPage1)
      else -> ListResponse(false, mockUsersPage2)
    }

    whenever(stackOverflowService.users(any(), eq(page), any()))
      .thenReturn(Observable.just(response))
  }

  private fun mockError(page: Int) {
    val error = Throwable()
    whenever(stackOverflowService.users(any(), eq(page), any()))
      .thenReturn(Observable.error(error))
  }
}
