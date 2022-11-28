package com.ihexep.presentation.search

import com.ihexep.domain.model.GithubRepositoryModel

data class RepositoryListState(
    val isLoading: Boolean = false,
    val repositories: List<GithubRepositoryModel> = emptyList(),
    val error: String = ""
)