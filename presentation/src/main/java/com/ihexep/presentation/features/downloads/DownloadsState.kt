package com.ihexep.presentation.features.downloads

import com.ihexep.domain.model.GithubRepositoryModel

data class DownloadsState(
    val isLoading: Boolean = false,
    val repositories: List<GithubRepositoryModel> = emptyList(),
    val error: String = ""
)
