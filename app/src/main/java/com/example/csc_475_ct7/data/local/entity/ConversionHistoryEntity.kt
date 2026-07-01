package com.example.csc_475_ct7.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversion_history")
data class ConversionHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val inputValue: String,
    val inputUnit: String,
    val outputValue: String,
    val outputUnit: String,
    val category: String,
    val timestamp: Long = System.currentTimeMillis()
)
