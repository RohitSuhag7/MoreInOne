package org.example.moreinone.repository

import kotlinx.coroutines.flow.Flow
import org.example.moreinone.model.dao.SettingsDao
import org.example.moreinone.model.entities.MoreSettings
import javax.inject.Inject

class MoreInOneRepository @Inject constructor(private val settingsDao: SettingsDao) {

    suspend fun insertSettings(moreSettings: MoreSettings) = settingsDao.insertSettings(moreSettings)

    fun getSettings(): Flow<MoreSettings> = settingsDao.getSettings()
}
