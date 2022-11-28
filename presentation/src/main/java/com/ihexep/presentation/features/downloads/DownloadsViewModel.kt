package com.ihexep.presentation.features.downloads

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihexep.domain.common.Resource
import com.ihexep.domain.usecases.GetLocalRepositoriesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DownloadsViewModel(
    private val getLocalRepositoriesUseCase: GetLocalRepositoriesUseCase
) : ViewModel() {

    private val _downloadsState = mutableStateOf(DownloadsState(isLoading = true))
    val downloadsState: State<DownloadsState> = _downloadsState

    init {
        getDownloads()
    }

    private fun getDownloads() {
        getLocalRepositoriesUseCase().onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _downloadsState.value = DownloadsState(isLoading = true)
                }
                is Resource.Loaded -> {
                    _downloadsState.value = DownloadsState(repositories = state.data)
                }
                is Resource.Error -> {
                    _downloadsState.value = DownloadsState(error = state.errorMessage)
                }
            }
        }.launchIn(viewModelScope)
    }
}