package com.androidengineers.masterly.domain.usecase

import com.androidengineers.masterly.data.local.Skill
import com.androidengineers.masterly.domain.repository.SkillsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSkillsUseCase @Inject constructor(
    private val skillsRepository: SkillsRepository
) {
    operator fun invoke(): Flow<List<Skill>> = skillsRepository.getSkills()
}