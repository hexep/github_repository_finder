package com.ihexep.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ihexep.domain.model.GithubRepositoryModel

@Entity(tableName = "repository_db", primaryKeys= [ "owner", "name" ])
data class DownloadedRepository(
    val owner: String,
    val name: String,
    val branch: String,
    val url: String
)

fun DownloadedRepository.toGithubRepository(): GithubRepositoryModel {
    return GithubRepositoryModel(
        owner = owner,
        name = name,
        branch = branch,
        url = url
    )
}

fun GithubRepositoryModel.toDownloadedRepository(): DownloadedRepository {
    return DownloadedRepository(
        owner = owner,
        name = name,
        branch = branch,
        url = url
    )
}