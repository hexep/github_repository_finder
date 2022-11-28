package com.ihexep.domain.usecases

import com.ihexep.domain.model.GithubRepositoryModel
import com.ihexep.domain.repository.GithubRepository

class AddDownloadedRepositoryUseCase(private val repository: GithubRepository) {
    suspend operator fun invoke(repositoryModel: GithubRepositoryModel) =
        repository.addRepositoryToDb(repositoryModel)
}