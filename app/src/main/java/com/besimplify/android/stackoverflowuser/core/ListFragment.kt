package com.besimplify.android.stackoverflowuser.core

import android.os.Bundle
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.besimplify.android.stackoverflowuser.R
import com.besimplify.android.stackoverflowuser.views.errorRow
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
    if (state.page == 0) {
      if (listRequest is Loading) {
        loadingRow { id("loading") }
      } else if (listRequest is Fail) {
        errorRow {
          id("error")
          errorIcon(R.drawable.ic_error_black_24dp)
          errorMessage(R.string.error_message)
          onClickListener { _ -> viewModel.fetchNextPage() }
        }
      }
    }

    state.list.forEach { item ->
      renderItem(item)
    }

    if (state.hasMore) {
      loadingRow {
        // Changing the ID will force it to rebind when new data is loaded even if it is
        // still on screen which will ensure that we trigger loading again.
        id("loading.${state.list.size}")
        onBind { _, _, _ -> viewModel.fetchNextPage() }
      }
    }
  }
}
