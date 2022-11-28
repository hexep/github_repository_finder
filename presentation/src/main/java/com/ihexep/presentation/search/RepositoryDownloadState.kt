package com.ihexep.presentation.search

data class RepositoryDownloadState(
    val isDownloading: Boolean = false,
    val isDownloaded: Boolean = false,
    val error: String = ""
)