package com.besimplify.android.stackoverflowuser.models

import com.squareup.moshi.Json

data class ReputationHistory(

  @Json(name = "reputation_history_type")
  val type: String,

  @Json(name = "user_id")
  val userId: Long,

  @Json(name = "post_id")
  val postId: Long,

  @Json(name = "reputation_change")
  val reputationChange: Int,

  @Json(name = "creation_date")
  val creationDate: Long
)
