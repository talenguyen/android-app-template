package com.besimplify.android.stackoverflowuser.features.userlist

import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.fragmentViewModel
import com.besimplify.android.stackoverflowuser.core.ListFragment
import com.besimplify.android.stackoverflowuser.extensions.format
import com.besimplify.android.stackoverflowuser.models.User
import com.besimplify.android.stackoverflowuser.views.userRow
import java.util.Date

class UserListFragment : ListFragment<User>() {

  override val viewModel: UserListViewModel by fragmentViewModel()

  override fun EpoxyController.renderItem(item: User) {
    userRow {
      id(item.id)
      profileImage(item.profileImage)
      displayName(item.name)
      reputation("${item.reputation}")
      location(item.location)
      lastAccessDate(Date(item.lastAccessDate).format())
    }
  }
}
