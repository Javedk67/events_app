package com.example.myeventsdemoapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myeventsdemoapp.data.repository.EventRepository


class SyncWorker(
    context: Context,
    params: WorkerParameters,
    private val repo: EventRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        repo.getEvents()
        return Result.success()
    }
}