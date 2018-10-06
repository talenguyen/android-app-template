package com.besimplify.android.stackoverflowuser.views

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.besimplify.android.stackoverflowuser.R
import com.squareup.picasso.Picasso

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class UserRow @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val profileImageView: ImageView
  private val displayNameTextView: TextView
  private val reputationTextView: TextView
  private val locationTextView: TextView
  private val lastAccessDateTextView: TextView

  init {
    inflate(context, R.layout.user_row, this)
    setBackgroundColor(Color.WHITE)

    profileImageView = findViewById(R.id.profileImageView)
    displayNameTextView = findViewById(R.id.displayNameTextView)
    reputationTextView = findViewById(R.id.reputationTextView)
    locationTextView = findViewById(R.id.locationTextView)
    lastAccessDateTextView = findViewById(R.id.lastAccessDateTextView)
  }

  @ModelProp
  fun setProfileImage(profileImage: String) {
    Picasso.get()
      .load(profileImage)
      .into(profileImageView)
  }

  @ModelProp
  fun setDisplayName(name: String) {
    displayNameTextView.text = name
  }

  @ModelProp
  fun setReputation(reputation: String) {
    reputationTextView.text = reputation
  }

  @ModelProp
  fun setLocation(location: String) {
    locationTextView.text = location
  }

  @ModelProp
  fun setLastAccessDate(lastAccessDate: String) {
    lastAccessDateTextView.text = lastAccessDate
  }
}
