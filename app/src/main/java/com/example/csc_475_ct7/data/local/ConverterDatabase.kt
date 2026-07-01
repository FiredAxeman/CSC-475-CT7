package com.example.csc_475_ct7.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.csc_475_ct7.data.local.dao.ConversionHistoryDao
import com.example.csc_475_ct7.data.local.entity.ConversionHistoryEntity

@Database(entities = [ConversionHistoryEntity::class], version = 1, exportSchema = false)
abstract class ConverterDatabase : RoomDatabase() {
    abstract fun historyDao(): ConversionHistoryDao
}
