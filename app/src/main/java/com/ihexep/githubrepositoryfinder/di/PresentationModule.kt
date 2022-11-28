package com.ihexep.githubrepositoryfinder.di

import com.ihexep.presentation.search.SearchViewModel
import com.ihexep.presentation.downloads.DownloadsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PresentationModule = module {
    viewModel {
        SearchViewModel(
            getNetworkRepositoriesByUserUseCase = get(),
            downloadRepositoryUseCase = get(),
            addDownloadedRepositoryUseCase = get()
        )
    }
    viewModel {
        DownloadsViewModel(
            getLocalRepositoriesUseCase = get()
        )
    }
}