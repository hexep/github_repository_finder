package com.ihexep.presentation.features.search

data class RepositoryDownloadState(
    val isDownloading: Boolean = false,
    val isDownloaded: Boolean = false,
    val error: String = ""
)