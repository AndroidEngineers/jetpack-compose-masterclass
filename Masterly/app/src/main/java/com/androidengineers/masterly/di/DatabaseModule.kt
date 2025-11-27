package com.androidengineers.masterly.di

import android.content.Context
import androidx.room.Room
import com.androidengineers.masterly.data.local.SkillDao
import com.androidengineers.masterly.data.local.SkillDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSkillDatabase(@ApplicationContext context: Context): SkillDatabase {
        return Room.databaseBuilder(
            context,
            SkillDatabase::class.java,
            "skill_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSkillDao(database: SkillDatabase): SkillDao {
        return database.skillDao()
    }
}