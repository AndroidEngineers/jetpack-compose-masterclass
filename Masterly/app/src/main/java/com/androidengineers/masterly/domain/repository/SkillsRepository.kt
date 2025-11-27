package com.androidengineers.masterly.domain.repository

import com.androidengineers.masterly.data.local.Skill
import kotlinx.coroutines.flow.Flow

interface SkillsRepository {

    fun getSkills(): Flow<List<Skill>>

    fun getSkillById(id: Int): Flow<Skill?>

    suspend fun addSkill(skill: Skill)

    suspend fun updateSkill(skill: Skill)

    suspend fun deleteSkill(skill: Skill)
}