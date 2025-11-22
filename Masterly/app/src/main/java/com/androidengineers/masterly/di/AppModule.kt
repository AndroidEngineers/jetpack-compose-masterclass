package com.androidengineers.masterly.di

import com.androidengineers.masterly.data.repository.SkillsRepositoryImpl
import com.androidengineers.masterly.domain.repository.SkillsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindSkillsRepository(skillsRepositoryImpl: SkillsRepositoryImpl): SkillsRepository
}