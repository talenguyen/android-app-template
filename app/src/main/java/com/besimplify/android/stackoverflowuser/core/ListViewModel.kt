package com.besimplify.android.stackoverflowuser.core

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.besimplify.android.stackoverflowuser.models.ListResponse
import io.reactivex.Observable

data class ListState<T>(
  val page: Int = 0,
  val list: List<T> = emptyList(),
  val listRequest: Async<ListResponse<T>> = Uninitialized,
  val hasMore: Boolean = false,
  val isConnected: Boolean = false
) : MvRxState

abstract class ListViewModel<T>(
  initialState: ListState<T>,
  debugMode: Boolean
) : MvRxViewModel<ListState<T>>(initialState, debugMode) {

  init {
    fetchNextPage()
  }

  protected abstract fun fetchList(page: Int): Observable<ListResponse<T>>

  fun setIsConnected(isConnected: Boolean) = setState { copy(isConnected = isConnected) }

  fun fetchNextPage() = withState { state ->
    if (state.listRequest is Loading) return@withState

    val nextPage = state.page + 1
    fetchList(nextPage).execute {
      copy(
        page = if (it is Success) nextPage else page,
        list = list + (it()?.items ?: emptyList()),
        listRequest = it,
        hasMore = it()?.hasMore ?: hasMore
      )
    }
  }
}
