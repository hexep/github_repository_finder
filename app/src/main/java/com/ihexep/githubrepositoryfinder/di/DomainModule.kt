package com.ihexep.githubrepositoryfinder.di

import com.ihexep.domain.usecases.AddDownloadedRepositoryUseCase
import com.ihexep.domain.usecases.DownloadRepositoryUseCase
import com.ihexep.domain.usecases.GetLocalRepositoriesUseCase
import com.ihexep.domain.usecases.GetNetworkRepositoriesByUserUseCase
import org.koin.dsl.module

val DomainModule = module {
    single { GetNetworkRepositoriesByUserUseCase(repository = get()) }
    single { DownloadRepositoryUseCase(repository = get()) }
    single { AddDownloadedRepositoryUseCase(repository = get()) }
    single { GetLocalRepositoriesUseCase(repository = get()) }
}