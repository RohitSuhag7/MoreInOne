package org.example.moreinone.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.moreinone.model.entities.MoreSettings

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: MoreSettings)

    @Query("SELECT * FROM SETTINGS_TABLE")
    fun getSettings(): Flow<MoreSettings>
}
