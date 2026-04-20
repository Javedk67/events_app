package com.example.myeventsdemoapp.di

import android.content.Context
import androidx.room.Room
import com.example.myeventsdemoapp.data.AppDatabase
import com.example.myeventsdemoapp.data.local.dao.EventDao
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
    fun provideDb(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "events_db"
        )
            .fallbackToDestructiveMigration() // for assignment simplicity
            .build()
    }

    @Provides
    @Singleton
    fun provideEventDao(db: AppDatabase): EventDao {
        return db.eventDao()
    }
}