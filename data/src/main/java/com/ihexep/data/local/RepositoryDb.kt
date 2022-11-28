package com.ihexep.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ihexep.data.local.dao.DownloadedRepositoryDao
import com.ihexep.data.local.entities.DownloadedRepository

@Database(
    entities = [DownloadedRepository::class],
    version = 1,
    exportSchema = false
)
abstract class RepositoryDb: RoomDatabase() {
    abstract fun getRepositoryDao(): DownloadedRepositoryDao
}