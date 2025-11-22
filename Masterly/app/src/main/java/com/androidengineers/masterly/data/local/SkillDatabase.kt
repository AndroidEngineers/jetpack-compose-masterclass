package com.androidengineers.masterly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Skill::class], version = 1, exportSchema = false)
abstract class SkillDatabase : RoomDatabase() {

    abstract fun skillDao(): SkillDao
}