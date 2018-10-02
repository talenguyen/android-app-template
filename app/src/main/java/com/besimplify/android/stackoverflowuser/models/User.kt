package com.besimplify.android.stackoverflowuser.models

import com.squareup.moshi.Json

data class User(

  @Json(name = "user_id")
  val id: Long,

  @Json(name = "display_name")
  val name: String,

  @Json(name = "profile_image")
  val profileImage: String,

  @Json(name = "reputation")
  val reputation: Long,

  @Json(name = "last_access_date")
  val lastAccessDate: String,

  @Json(name = "location")
  val location: String = ""
)
