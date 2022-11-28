package com.ihexep.githubrepositoryfinder.di

import androidx.room.Room
import com.ihexep.data.common.Constants.GITHUB_API_URL
import com.ihexep.data.datasource.local.LocalDataSource
import com.ihexep.data.datasource.local.LocalDataSourceImpl
import com.ihexep.data.datasource.network.NetworkDataSource
import com.ihexep.data.datasource.network.NetworkDataSourceImpl
import com.ihexep.data.local.RepositoryDb
import com.ihexep.data.local.dao.DownloadedRepositoryDao
import com.ihexep.data.network.api.GithubApi
import com.ihexep.data.network.createWebService
import com.ihexep.data.repository.GithubRepositoryImpl
import com.ihexep.domain.repository.GithubRepository
import org.koin.dsl.module

val DataModule = module {
    // Local data
    single { Room.databaseBuilder(get(), RepositoryDb::class.java, "repository_db" ).build() }
    single { get<RepositoryDb>().getRepositoryDao() }
    single { provideLocalDataSource(dao = get()) }

    // Network data
    single { createWebService<GithubApi>(url = GITHUB_API_URL) }
    single { provideNetworkDataSource(api = get()) }

    // Main repository
    single { provideGithubUserRepository(localDataSource = get(), networkDataSource = get()) }
}

fun provideLocalDataSource(dao: DownloadedRepositoryDao): LocalDataSource {
    return LocalDataSourceImpl(dao)
}

fun provideNetworkDataSource(api: GithubApi): NetworkDataSource {
    return NetworkDataSourceImpl(api)
}

fun provideGithubUserRepository(
    localDataSource: LocalDataSource,
    networkDataSource: NetworkDataSource
): GithubRepository {
    return GithubRepositoryImpl(localDataSource, networkDataSource)
}