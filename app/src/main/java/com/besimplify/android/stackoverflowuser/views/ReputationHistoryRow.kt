package com.besimplify.android.stackoverflowuser.views

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.TextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.besimplify.android.stackoverflowuser.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ReputationHistoryRow @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val reputationChangeTextView: TextView
  private val typeTextView: TextView
  private val postIdTextView: TextView
  private val lastAccessDateTextView: TextView

  init {
    inflate(context, R.layout.reputation_history_row, this)
    setBackgroundColor(Color.WHITE)

    reputationChangeTextView = findViewById(R.id.reputationChangeTextView)
    typeTextView = findViewById(R.id.typeTextView)
    postIdTextView = findViewById(R.id.postIdTextView)
    lastAccessDateTextView = findViewById(R.id.lastAccessDateTextView)
  }

  @ModelProp
  fun setReputationChange(value: Int) {
    if (value < 0) {
      reputationChangeTextView.setTextColor(Color.RED)
    } else {
      reputationChangeTextView.setTextColor(Color.GREEN)
    }

    val displayText = when {
      value > 0 -> "+$value"
      else -> "$value"
    }

    reputationChangeTextView.text = displayText
  }

  @ModelProp
  fun setType(type: String) {
    typeTextView.text = type
  }

  @ModelProp
  fun setPostId(postId: String) {
    postIdTextView.text = postId
  }

  @ModelProp
  fun setLastAccessDate(lastAccessDate: String) {
    lastAccessDateTextView.text = lastAccessDate
  }
}
