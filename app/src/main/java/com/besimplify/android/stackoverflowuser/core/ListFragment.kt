package com.besimplify.android.stackoverflowuser.core

import android.os.Bundle
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.besimplify.android.stackoverflowuser.views.loadingRow

abstract class ListFragment<T> : BaseFragment() {

  protected abstract val viewModel: ListViewModel<T>

  protected abstract fun EpoxyController.renderItem(item: T)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recyclerView.setItemSpacingDp(1)
  }

  override fun epoxyController() = simpleController(viewModel) { state ->
    val listRequest = state.listRequest
    if (state.page == 0 && listRequest is Loading) {
      loadingRow { id("loading") }
    }

    state.list.forEach { item ->
      renderItem(item)
    }

    if (listRequest is Success && listRequest().hasMore) {
      loadingRow {
        // Changing the ID will force it to rebind when new data is loaded even if it is
        // still on screen which will ensure that we trigger loading again.
        id("loading.${state.list.size}")
        onBind { _, _, _ -> viewModel.fetchNextPage() }
      }
    }
  }
}
