package com.androidengineers.masterly.data.repository

import com.androidengineers.masterly.data.local.Skill
import com.androidengineers.masterly.data.local.SkillDao
import com.androidengineers.masterly.domain.repository.SkillsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

class SkillsRepositoryImpl @Inject constructor(
    private val skillDao: SkillDao
) : SkillsRepository {

    override fun getSkills(): Flow<List<Skill>> {
        return skillDao.getAllSkills()
    }

    override suspend fun addSkill(skill: Skill) {
        skillDao.insert(skill)
    }
}