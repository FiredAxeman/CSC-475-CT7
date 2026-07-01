package com.example.csc_475_ct7.di

import android.content.Context
import androidx.room.Room
import com.example.csc_475_ct7.data.local.ConverterDatabase
import com.example.csc_475_ct7.data.local.dao.ConversionHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ConverterDatabase {
        return Room.databaseBuilder(
            context,
            ConverterDatabase::class.java,
            "converter_db"
        ).build()
    }

    @Provides
    fun provideHistoryDao(database: ConverterDatabase): ConversionHistoryDao {
        return database.historyDao()
    }
}
