package com.ihexep.data.datasource.network

import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.GithubRepositoryModel
import kotlinx.coroutines.flow.Flow
import java.io.File

interface NetworkDataSource {
    fun getRepositories(userName: String): Flow<Resource<List<GithubRepositoryModel>>>
    fun downloadRepository(repository: GithubRepositoryModel): Flow<Resource<File>>
}