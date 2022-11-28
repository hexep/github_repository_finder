package com.ihexep.data.repository

import com.ihexep.data.datasource.local.LocalDataSource
import com.ihexep.data.datasource.network.NetworkDataSource
import com.ihexep.domain.model.GithubRepositoryModel
import com.ihexep.domain.repository.GithubRepository

class GithubRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) : GithubRepository {
    override fun getLocalRepositories() =
        localDataSource.getRepositories()

    override fun getNetworkRepositoriesByUser(userName: String) =
        networkDataSource.getRepositories(userName)

    override fun downloadRepository(repository: GithubRepositoryModel) =
        networkDataSource.downloadRepository(repository)

    override suspend fun addRepositoryToDb(repository: GithubRepositoryModel) =
        localDataSource.addRepository(repository)
}