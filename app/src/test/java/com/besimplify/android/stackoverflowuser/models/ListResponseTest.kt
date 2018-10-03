package com.besimplify.android.stackoverflowuser.models

import com.besimplify.android.stackoverflowuser.util.asBufferedSources
import com.besimplify.android.stackoverflowuser.util.resourcesFile
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.*

class ListResponseTest {

  private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

  @Test
  fun parseUserList() {
    val userListResponse = userListAdapter().fromJson(resourcesFile("users.json").asBufferedSources())
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

  @Test
  fun parseReputationHistoryList() {
    val reputationHistoryListResponse =
      reputationHistoryListAdapter().fromJson(resourcesFile("reputation_histories.json").asBufferedSources())

    Assert.assertNotNull(reputationHistoryListResponse)
    Assert.assertEquals(30, reputationHistoryListResponse?.items?.size)
    Assert.assertEquals(
      ReputationHistory(
        "post_upvoted",
        22656,
        8544460,
        0,
        1538405612
      ),
      reputationHistoryListResponse?.items?.get(0)
    )
  }

  private fun userListAdapter(): JsonAdapter<ListResponse<User>> {
    val listResponse = Types.newParameterizedType(ListResponse::class.java, User::class.java)
    return moshi.adapter(listResponse)
  }

  private fun reputationHistoryListAdapter(): JsonAdapter<ListResponse<ReputationHistory>> {
    val listResponse = Types.newParameterizedType(ListResponse::class.java, ReputationHistory::class.java)
    return moshi.adapter(listResponse)
  }
}
