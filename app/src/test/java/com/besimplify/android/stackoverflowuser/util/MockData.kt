package com.besimplify.android.stackoverflowuser.util

import com.besimplify.android.stackoverflowuser.models.ListResponse
import com.besimplify.android.stackoverflowuser.models.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

private val moshi = Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .build()

fun userListAdapter(): JsonAdapter<ListResponse<User>> {
  val listResponse = Types.newParameterizedType(ListResponse::class.java, User::class.java)
  return moshi.adapter(listResponse)
}

fun Any.mockUserListResponse(): ListResponse<User> {
  return userListAdapter().fromJson(resourcesFile("users.json").asBufferedSources())
    ?: throw IllegalArgumentException("can't create user response")
}
