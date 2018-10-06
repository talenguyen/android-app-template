package com.besimplify.android.stackoverflowuser.core

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

class ListViewModelTest {
  // A rule that makes all subscriptions to be subscribed and observed on a trampoline scheduler
  @get:Rule
  val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

  private val stackOverflowService: StackOverflowService = mock()
  private val mockList = mockUserListResponse().items
  private val mockListPage1 = mockList.take(15)
  private val mockListPage2 = mockList.takeLast(mockList.size - 15)

  @Test
  fun fetchFirstPageError() {
    mockError(1)

    val tested = makeListViewModel()

    withState(tested) {
      assertThat(it.listRequest is Fail).isTrue()
    }
  }

  @Test
  fun fetchFirstPageSuccess() {
    mockSuccess(1)

    val tested = makeListViewModel()

    withState(tested) {
      assertThat(it.listRequest is Success).isTrue()
      assertThat(it.list).isEqualTo(mockListPage1)
    }
  }

  @Test
  fun fetchMorePageError() {
    // Mock first page success
    mockSuccess(1)

    // Mock second page error
    mockError(2)

    // First page success
    val tested = makeListViewModel()

    // Load second page
    tested.fetchNextPage()

    withState(tested) {
      assertThat(it.list).isEqualTo(mockListPage1)
      assertThat(it.listRequest is Fail).isTrue()
    }
  }

  @Test
  fun fetchMorePageSuccess() {
    // Mock first page success
    mockSuccess(1)

    // Mock second page error
    mockSuccess(2)

    // First page success
    val tested = makeListViewModel()

    // Load second page
    tested.fetchNextPage()

    withState(tested) {
      assertThat(it.list).isEqualTo(mockListPage1 + mockListPage2)
      assertThat(it.listRequest is Success).isTrue()
    }
  }

  private fun makeListViewModel(): ListViewModel<User> {
    return object : ListViewModel<User>(ListState(), true) {
      override fun fetchList(page: Int): Observable<ListResponse<User>> = stackOverflowService.users(page = page)
    }
  }

  private fun mockSuccess(page: Int) {
    val response: ListResponse<User> = when (page) {
      1 -> ListResponse(true, mockListPage1)
      else -> ListResponse(false, mockListPage2)
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
