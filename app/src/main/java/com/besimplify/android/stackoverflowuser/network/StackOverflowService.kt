package com.besimplify.android.stackoverflowuser.network

import com.besimplify.android.stackoverflowuser.models.ListResponse
import com.besimplify.android.stackoverflowuser.models.ReputationHistory
import com.besimplify.android.stackoverflowuser.models.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface StackOverflowService {

  @Headers("Accept: application/json;charset=utf-t")
  @GET("users")
  fun users(
    @Query("site") site: String = "stackoverflow",
    @Query("page") page: Int = 1,
    @Query("pagesize") limit: Int = 30
  ): Observable<ListResponse<User>>

  @Headers("Accept: application/json;charset=utf-t")
  @GET("users/{user_id}/reputation-history")
  fun reputationHistory(
    @Path("user_id") userId: Long,
    @Query("site") site: String = "stackoverflow",
    @Query("page") page: Int = 1,
    @Query("pagesize") limit: Int = 30
  ): Observable<ListResponse<ReputationHistory>>
}
