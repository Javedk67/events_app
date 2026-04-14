package com.example.myeventsdemoapp.di

import android.content.Context
import androidx.room.Room

import com.example.myeventsdemoapp.data.AppDatabase
import com.example.myeventsdemoapp.data.local.dao.EventDao
import com.example.myeventsdemoapp.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApi(): ApiService =
        Retrofit.Builder()
            .baseUrl("https://mocki.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    fun provideDb(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "db").build()

    @Provides
    fun provideDao(db: AppDatabase): EventDao = db.eventDao()
}