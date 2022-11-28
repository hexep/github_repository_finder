package com.ihexep.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.GithubRepositoryModel
import com.ihexep.domain.usecases.AddDownloadedRepositoryUseCase
import com.ihexep.domain.usecases.DownloadRepositoryUseCase
import com.ihexep.domain.usecases.GetNetworkRepositoriesByUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchViewModel(
    private val getNetworkRepositoriesByUserUseCase: GetNetworkRepositoriesByUserUseCase,
    private val downloadRepositoryUseCase: DownloadRepositoryUseCase,
    private val addDownloadedRepositoryUseCase: AddDownloadedRepositoryUseCase,
) : ViewModel() {

    private val _searchState = mutableStateOf(RepositoryListState())
    val searchState: State<RepositoryListState> = _searchState

    private val _downloadState = mutableStateMapOf<Int, RepositoryDownloadState>()
    val downloadState: Map<Int, RepositoryDownloadState> = _downloadState

    private val _errorState = MutableSharedFlow<String>()
    val errorState = _errorState.asSharedFlow()

    fun getRepositories(userName: String) {
        getNetworkRepositoriesByUserUseCase(userName).onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _searchState.value = RepositoryListState(isLoading = true)
                }
                is Resource.Loaded -> {
                    _searchState.value = RepositoryListState(repositories = state.data)
                }
                is Resource.Error -> {
                    _searchState.value = RepositoryListState(error = state.errorMessage)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun downloadRepository(repository: GithubRepositoryModel) {
        downloadRepositoryUseCase(repository).onEach { state ->
            val downloadId = repository.hashCode()
            when (state) {
                is Resource.Loading -> {
                    _downloadState[downloadId] = RepositoryDownloadState(isDownloading = true)
                }
                is Resource.Loaded -> {
                    _downloadState[downloadId] = RepositoryDownloadState(isDownloaded = true)
                    addDownloadedRepositoryUseCase(repository)
                }
                is Resource.Error -> {
                    _errorState.emit("${repository.name}: ${state.errorMessage}")
                    _downloadState.remove(downloadId)
                }
            }
        }.launchIn(viewModelScope)
    }
}