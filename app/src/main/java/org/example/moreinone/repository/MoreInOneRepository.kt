package org.example.moreinone.repository

import kotlinx.coroutines.flow.Flow
import org.example.moreinone.model.dao.SettingsDao
import org.example.moreinone.model.entities.Settings
import javax.inject.Inject

class MoreInOneRepository @Inject constructor(private val settingsDao: SettingsDao) {

    suspend fun insertSettings(settings: Settings) = settingsDao.insertSettings(settings)

    fun getSettings(): Flow<Settings> = settingsDao.getSettings()
}
