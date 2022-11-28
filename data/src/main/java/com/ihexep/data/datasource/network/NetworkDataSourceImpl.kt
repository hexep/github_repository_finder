package com.ihexep.data.datasource.network

import android.os.Environment
import com.ihexep.data.network.api.GithubApi
import com.ihexep.data.network.dto.toGithubRepository
import com.ihexep.domain.common.Resource
import com.ihexep.domain.model.GithubRepositoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class NetworkDataSourceImpl(private val api: GithubApi) : NetworkDataSource {
    override fun getRepositories(userName: String): Flow<Resource<List<GithubRepositoryModel>>> =
        flow {
            try {
                emit(Resource.Loading)
                val response = api.getUserRepositoryList(userName)
                val result = response.mapNotNull { it.toGithubRepository() }
                emit(Resource.Loaded(result))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "unexpected error"))
            }
        }

    override fun downloadRepository(repository: GithubRepositoryModel): Flow<Resource<File>> =
        flow {
            try {
                emit(Resource.Loading)
                val timestamp = SimpleDateFormat("yyyy.MM.dd_HH.mm.ss", Locale.getDefault())
                    .format(Date())
                val destinationPath = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val destinationFile = File(destinationPath, "${repository.name}_($timestamp).zip")
                api.downloadRepository(repository.owner, repository.name, repository.branch)
                    .saveToFile(destinationFile)
                emit(Resource.Loaded(destinationFile))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "unexpected error"))
            }
        }.flowOn(Dispatchers.IO)

    private fun ResponseBody.saveToFile(file: File) {
        byteStream().use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}