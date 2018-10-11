package com.besimplify.android.stackoverflowuser.views

import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.besimplify.android.stackoverflowuser.R
import com.besimplify.android.stackoverflowuser.extensions.dip

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class DateRow @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

  init {
    if (VERSION.SDK_INT >= VERSION_CODES.M) {
      setTextAppearance(R.style.TextAppearance_AppCompat_Body2)
    } else {
      setTextAppearance(context, R.style.TextAppearance_AppCompat_Body2)
    }
    val horizontalPadding = context.dip(16)
    val verticalPadding = context.dip(12)
    setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
  }

  @ModelProp
  fun setDateText(text: String) {
    setText(text)
  }
}
