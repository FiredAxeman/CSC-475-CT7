package com.example.csc_475_ct7.data.repository

import com.example.csc_475_ct7.data.local.dao.ConversionHistoryDao
import com.example.csc_475_ct7.data.local.entity.ConversionHistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyDao: ConversionHistoryDao
) {
    fun getAllHistory(): Flow<List<ConversionHistoryEntity>> = historyDao.getAllHistory()

    suspend fun insertHistory(history: ConversionHistoryEntity) {
        historyDao.insertHistory(history)
    }

    suspend fun clearHistory() {
        historyDao.clearHistory()
    }
}
