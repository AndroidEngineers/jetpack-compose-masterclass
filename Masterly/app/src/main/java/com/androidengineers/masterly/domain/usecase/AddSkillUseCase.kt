package com.androidengineers.masterly.domain.usecase

import com.androidengineers.masterly.data.local.Skill
import com.androidengineers.masterly.domain.repository.SkillsRepository
import javax.inject.Inject

class AddSkillUseCase @Inject constructor(
    private val skillsRepository: SkillsRepository
) {
    suspend operator fun invoke(skill: Skill) = skillsRepository.addSkill(skill)
}