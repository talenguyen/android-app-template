package com.besimplify.android.stackoverflowuser.views

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.besimplify.android.stackoverflowuser.R
import com.besimplify.android.stackoverflowuser.extensions.dip

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ErrorRow @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  private val errorIconImageView: ImageView
  private val errorMessageTextView: TextView

  init {
    inflate(context, R.layout.error_row, this)
    val padding = context.dip(32)
    setPadding(padding, padding, padding, padding)

    errorIconImageView = findViewById(R.id.errorIconImageView)
    errorMessageTextView = findViewById(R.id.errorMessageTextView)
  }

  @ModelProp
  fun setErrorIcon(@DrawableRes id: Int) {
    errorIconImageView.setImageResource(id)
  }

  @TextProp
  fun setErrorMessage(text: CharSequence?) {
    errorMessageTextView.text = text
  }

  @CallbackProp
  override fun setOnClickListener(l: OnClickListener?) {
    super.setOnClickListener(l)
  }
}
