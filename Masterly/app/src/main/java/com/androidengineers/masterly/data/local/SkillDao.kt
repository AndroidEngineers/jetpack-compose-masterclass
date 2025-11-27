package com.androidengineers.masterly.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillDao {

    @Insert
    suspend fun insert(skill: Skill)

    @Query("SELECT * FROM skills")
    fun getAllSkills(): Flow<List<Skill>>

    @Query("SELECT * FROM skills WHERE id = :id")
    fun getSkillById(id: Int): Flow<Skill?>

    @androidx.room.Update
    suspend fun update(skill: Skill)

    @androidx.room.Delete
    suspend fun delete(skill: Skill)
}