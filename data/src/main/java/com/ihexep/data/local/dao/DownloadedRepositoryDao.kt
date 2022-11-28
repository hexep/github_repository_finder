package com.ihexep.data.local.dao

import androidx.room.*
import com.ihexep.data.local.entities.DownloadedRepository
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedRepositoryDao {
    @Query("SELECT * FROM repository_db")
    fun getAll(): Flow<List<DownloadedRepository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg downloadedGithubRepository: DownloadedRepository)
}