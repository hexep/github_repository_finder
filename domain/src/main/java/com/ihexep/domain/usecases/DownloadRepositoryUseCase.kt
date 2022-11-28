package com.ihexep.domain.usecases

import com.ihexep.domain.model.GithubRepositoryModel
import com.ihexep.domain.repository.GithubRepository

class DownloadRepositoryUseCase(private val repository: GithubRepository) {
    operator fun invoke(repositoryModel: GithubRepositoryModel) =
        repository.downloadRepository(repositoryModel)
}