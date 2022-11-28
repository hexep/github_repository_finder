package com.ihexep.domain.repository

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.GithubRepositoryModel
import kotlinx.coroutines.flow.Flow
import java.io.File

interface GithubRepository {
    fun getLocalRepositories(): Flow<Resource<List<GithubRepositoryModel>>>
    fun getNetworkRepositoriesByUser(userName: String): Flow<Resource<List<GithubRepositoryModel>>>
    fun downloadRepository(repository: GithubRepositoryModel): Flow<Resource<File>>
    suspend fun addRepositoryToDb(repository: GithubRepositoryModel)
}