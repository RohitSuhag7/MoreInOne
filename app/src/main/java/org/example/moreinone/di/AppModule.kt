package org.example.moreinone.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.example.moreinone.model.dao.NotesDao
import org.example.moreinone.model.dao.TodoDao
import org.example.moreinone.model.dao.SettingsDao
import org.example.moreinone.model.database.LocalDatabase
import org.example.moreinone.repository.MoreInOneRepository
import org.example.moreinone.repository.NotesRepository
import org.example.moreinone.repository.TodoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(localDB: LocalDatabase): TodoDao = localDB.todoDao()

    @Provides
    @Singleton
    fun provideSettingsDao(localDB: LocalDatabase): SettingsDao = localDB.settingsDao()

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository = TodoRepository(todoDao)

    @Provides
    @Singleton
    fun provideMoreInOneRepository(settingsDao: SettingsDao): MoreInOneRepository =
        MoreInOneRepository(settingsDao)

    @Provides
    @Singleton
    fun provideNotesDao(localDB: LocalDatabase): NotesDao = localDB.notesDao()

    @Provides
    @Singleton
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository = NotesRepository(notesDao)
}
