package com.besimplify.android.stackoverflowuser.features.userlist

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.besimplify.android.stackoverflowuser.core.BaseFragment
import com.besimplify.android.stackoverflowuser.core.simpleController
import com.besimplify.android.stackoverflowuser.extensions.format
import com.besimplify.android.stackoverflowuser.views.loadingRow
import com.besimplify.android.stackoverflowuser.views.userRow
import java.util.Date

class UserListFragment : BaseFragment() {

  private val viewModel: UserListViewModel by fragmentViewModel()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recyclerView.setItemSpacingDp(1)
  }

  override fun epoxyController() = simpleController(viewModel) { state ->
    val usersRequest = state.usersRequest
    if (state.page == 0 && usersRequest is Loading) {
      loadingRow { id("loading") }
    }

    state.users.forEach { user ->
      userRow {
        id(user.id)
        profileImage(user.profileImage)
        displayName(user.name)
        reputation("${user.reputation}")
        location(user.location)
        lastAccessDate(Date(user.lastAccessDate).format())
      }
    }

    if (usersRequest is Success && usersRequest().hasMore) {
      loadingRow {
        // Changing the ID will force it to rebind when new data is loaded even if it is
        // still on screen which will ensure that we trigger loading again.
        id("loading.${state.users.size}")
        onBind { _, _, _ -> viewModel.fetchNextPage() }
      }
    }
  }
}
