package com.ihexep.data.network.api

import com.ihexep.data.network.dto.RepositoryListDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface GithubApi {
    @GET("/users/{user}/repos")
    suspend fun getUserRepositoryList(@Path("user") user: String): RepositoryListDto

    @Streaming
    @GET("/repos/{owner}/{repo}/zipball/{ref}")
    suspend fun downloadRepository(
        @Path("owner") owner: String,
        @Path("repo")  repository: String,
        @Path("ref")   branch: String
    ): ResponseBody
}