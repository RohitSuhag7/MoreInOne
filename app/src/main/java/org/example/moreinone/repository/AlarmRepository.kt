package org.example.moreinone.repository

import kotlinx.coroutines.flow.Flow
import org.example.moreinone.model.dao.AlarmDao
import org.example.moreinone.model.entities.Alarm
import javax.inject.Inject

class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao) {

    suspend fun addAlarm(alarm: Alarm) = alarmDao.addAlarm(alarm)

    suspend fun deleteAlarm(alarm: Alarm) = alarmDao.deleteAlarm(alarm)

    fun getAllAlarm(): Flow<List<Alarm>> = alarmDao.getAllAlarm()
}
