package com.besimplify.android.stackoverflowuser.models

import com.squareup.moshi.Json

class ListResponse<T>(

  @Json(name = "has_more")
  val hasMore: Boolean,

  @Json(name = "items")
  val items: List<T> = emptyList()

)
