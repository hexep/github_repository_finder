package com.ihexep.presentation.features.search

import com.ihexep.domain.model.GithubRepositoryModel

data class RepositoryListState(
    val isLoading: Boolean = false,
    val repositories: List<GithubRepositoryModel> = emptyList(),
    val error: String = ""
)