package com.example.githubsearch.api

import com.example.githubsearch.models.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    suspend fun getGitHubUsersList(
        @Query("q") query: String
    ): Response<UserModel>
}