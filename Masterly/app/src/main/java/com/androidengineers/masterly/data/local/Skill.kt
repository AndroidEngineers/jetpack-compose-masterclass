package com.androidengineers.masterly.data.local

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skills")
data class Skill(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val minutesPracticed: Int,
    val goalMinutes: Int
)