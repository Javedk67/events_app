package com.example.myeventsdemoapp

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.myeventsdemoapp.data.AppDatabase
import com.example.myeventsdemoapp.data.remote.api.ApiService
import com.example.myeventsdemoapp.data.repository.EventRepository
import com.example.myeventsdemoapp.worker.MyWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class MyApp: Application(){
    lateinit var workerFactory: MyWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Log.d("APP_CHECK", "MyApp initialized")
        // ❗ You must manually create dependencies here
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "events_db"
        ).build()

        val dao = db.eventDao()
        // 🔥 Create Retrofit manually
        val retrofit = Retrofit.Builder()
            .baseUrl("https://free.mockerapi.com/mock/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

// ✅ Correct repo creation
        val repo = EventRepository(api, dao)


        workerFactory = MyWorkerFactory(repo)





//
//        WorkManager.initialize(
//            this,
//            Configuration.Builder()
//                .setWorkerFactory(workerFactory)
//                .build()
//        )
    }
//    override fun getWorkManagerConfiguration(): Configuration {
//        return Configuration.Builder()
//            .setWorkerFactory(workerFactory) // 🔥 HERE IT IS USED
//            .build()
//    }
}