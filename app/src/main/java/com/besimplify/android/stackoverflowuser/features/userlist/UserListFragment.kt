package com.besimplify.android.stackoverflowuser.features.userlist

import android.os.Bundle
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.fragmentViewModel
import com.besimplify.android.stackoverflowuser.R
import com.besimplify.android.stackoverflowuser.core.ListFragment
import com.besimplify.android.stackoverflowuser.extensions.formatDate
import com.besimplify.android.stackoverflowuser.models.User
import com.besimplify.android.stackoverflowuser.views.userRow
import java.util.Date

class UserListFragment : ListFragment<User>() {

  override val viewModel: UserListViewModel by fragmentViewModel()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    toolbar.title = getString(R.string.app_name)
  }

  override fun EpoxyController.renderList(list: List<User>) {
    list.forEach { user ->
      userRow {
        id("user.${user.id}")
        profileImage(user.profileImage)
        displayName(user.name)
        reputation("${user.reputation}")
        location(user.location)
        lastAccessDate(Date(user.lastAccessDate * 1000).formatDate())
        onClickListener { _ -> navigateTo(R.id.action_userList_to_reputationHistory, user) }
      }
    }
  }
}
