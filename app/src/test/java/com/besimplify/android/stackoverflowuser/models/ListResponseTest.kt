package com.besimplify.android.stackoverflowuser.models

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.Okio
import org.junit.Assert
import org.junit.Test
import java.io.File

class ListResponseTest {

  @Test
  fun parseUserList() {
    val classLoader = javaClass.classLoader
    val resource = classLoader.getResource("users.json")
    val file = File(resource.path)
    val userListResponse = userListAdapter().fromJson(Okio.buffer(Okio.source(file)))
    Assert.assertNotNull(userListResponse)
    Assert.assertEquals(30, userListResponse?.items?.size)
    Assert.assertEquals(
      User(
        22656,
        "Jon Skeet",
        "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=128&amp;d=identicon&amp;r=PG",
        1057823,
        1538509483,
        "Reading, United Kingdom"
      ),
      userListResponse?.items?.get(0)
    )
  }

  private fun userListAdapter(): JsonAdapter<ListResponse<User>> {
    val moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
    val listMyData = Types.newParameterizedType(ListResponse::class.java, User::class.java)
    return moshi.adapter(listMyData)
  }
}