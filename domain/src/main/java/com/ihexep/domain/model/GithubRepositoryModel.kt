package com.ihexep.domain.model

data class GithubRepositoryModel(
    val owner: String,
    val name: String,
    val branch: String,
    val url: String
) {
    override fun hashCode(): Int {
        return "$owner.$name".hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GithubRepositoryModel

        if (owner != other.owner) return false
        if (name != other.name) return false

        return true
    }
}