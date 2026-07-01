package com.example.csc_475_ct7.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.csc_475_ct7.data.local.entity.ConversionHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversionHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: ConversionHistoryEntity)

    @Query("SELECT * FROM conversion_history ORDER BY timestamp DESC LIMIT 50")
    fun getAllHistory(): Flow<List<ConversionHistoryEntity>>

    @Query("DELETE FROM conversion_history")
    suspend fun clearHistory()
}
