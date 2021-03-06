package com.besimplify.android.stackoverflowuser.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
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
  val lastAccessDate: Long,

  @Json(name = "location")
  val location: String = ""
) : Parcelable
