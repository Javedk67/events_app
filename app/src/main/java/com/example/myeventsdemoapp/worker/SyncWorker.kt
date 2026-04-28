package com.example.myeventsdemoapp.worker

import android.content.Context

import androidx.room.Room

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myeventsdemoapp.data.AppDatabase
import com.example.myeventsdemoapp.data.remote.api.ApiService
import com.example.myeventsdemoapp.data.repository.EventRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SyncWorker (
    context: Context,
    params: WorkerParameters,
    private val repo: EventRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {

            repo.getEvents().first()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}