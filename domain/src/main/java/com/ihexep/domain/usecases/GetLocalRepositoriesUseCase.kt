package com.ihexep.domain.usecases

import com.ihexep.domain.repository.GithubRepository

class GetLocalRepositoriesUseCase(private val repository: GithubRepository) {
    operator fun invoke() = repository.getLocalRepositories()
}