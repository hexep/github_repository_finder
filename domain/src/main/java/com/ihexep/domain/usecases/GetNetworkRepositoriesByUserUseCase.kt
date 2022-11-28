package com.ihexep.domain.usecases

import com.ihexep.domain.repository.GithubRepository

class GetNetworkRepositoriesByUserUseCase(private val repository: GithubRepository) {
    operator fun invoke(userName: String) = repository.getNetworkRepositoriesByUser(userName)
}