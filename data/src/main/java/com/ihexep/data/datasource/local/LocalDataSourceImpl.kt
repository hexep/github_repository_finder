package com.ihexep.data.datasource.local

import com.ihexep.data.local.dao.DownloadedRepositoryDao
import com.ihexep.data.local.entities.toDownloadedRepository
import com.ihexep.data.local.entities.toGithubRepository
import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.GithubRepositoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(private val dao: DownloadedRepositoryDao) : LocalDataSource {
    override fun getRepositories(): Flow<Resource<List<GithubRepositoryModel>>> =
        dao.getAll().map { repositoryList ->
            Resource.Loaded(repositoryList.map { repository ->
                repository.toGithubRepository()
            })
        }

    override suspend fun addRepository(repository: GithubRepositoryModel) {
        dao.insert(repository.toDownloadedRepository())
    }
}