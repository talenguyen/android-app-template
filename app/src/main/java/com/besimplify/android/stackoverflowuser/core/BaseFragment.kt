package com.besimplify.android.stackoverflowuser.core

import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.IdRes
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRx
import com.besimplify.android.stackoverflowuser.R

abstract class BaseFragment : BaseMvRxFragment() {

  protected lateinit var recyclerView: EpoxyRecyclerView
  protected lateinit var toolbar: Toolbar
  protected lateinit var coordinatorLayout: CoordinatorLayout
  protected val epoxyController by lazy { epoxyController() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    epoxyController.onRestoreInstanceState(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_mvrx_base, container, false).apply {
      recyclerView = findViewById(R.id.recyclerView)
      toolbar = findViewById(R.id.toolbar)
      coordinatorLayout = findViewById(R.id.coordinator)

      recyclerView.setController(epoxyController)

      toolbar.setupWithNavController(findNavController())
    }
  }

  override fun invalidate() {
    recyclerView.requestModelBuild()
  }

  /**
   * Provide the EpoxyController to use when building models for this Fragment.
   * Basic usages can simply use [simpleController]
   */
  abstract fun epoxyController(): MvRxEpoxyController

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    epoxyController.onSaveInstanceState(outState)
  }

  override fun onDestroyView() {
    epoxyController.cancelPendingModelBuild()
    super.onDestroyView()
  }

  protected fun navigateTo(@IdRes actionId: Int, arg: Parcelable? = null) {
    val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
    findNavController().navigate(actionId, bundle)
  }
}
