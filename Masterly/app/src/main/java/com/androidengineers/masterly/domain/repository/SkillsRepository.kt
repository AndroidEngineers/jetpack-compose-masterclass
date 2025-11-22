package com.androidengineers.masterly.domain.repository

import com.androidengineers.masterly.data.local.Skill
import kotlinx.coroutines.flow.Flow

interface SkillsRepository {

    fun getSkills(): Flow<List<Skill>>

    suspend fun addSkill(skill: Skill)
}