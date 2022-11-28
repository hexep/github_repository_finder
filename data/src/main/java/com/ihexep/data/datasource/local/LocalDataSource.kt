package com.ihexep.data.datasource.local

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.GithubRepositoryModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getRepositories(): Flow<Resource<List<GithubRepositoryModel>>>
    suspend fun addRepository(repository: GithubRepositoryModel)
}