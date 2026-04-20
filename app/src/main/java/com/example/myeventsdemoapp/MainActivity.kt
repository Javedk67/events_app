package com.example.myeventsdemoapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.myeventsdemoapp.databinding.ActivityMainBinding
import com.example.myeventsdemoapp.ui.main.EventAdapter
import com.example.myeventsdemoapp.ui.main.EventViewModel
import com.example.myeventsdemoapp.utils.distanceKm
import com.example.myeventsdemoapp.worker.SyncWorker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: EventViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var userLat: Double? = null
    private var userLng: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set content view
        setContentView(binding.root)
        // WorkManager

        val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            6, TimeUnit.HOURS // ✅ low frequency
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED) // only when internet
                    .setRequiresBatteryNotLow(true) // avoid low battery
                    .build()
            )
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                30,
                TimeUnit.SECONDS
            )
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "event_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestLocationPermission()

        var adapter = EventAdapter(
            onBookmark = { event,isMark->
                vm.toggle(event,isMark)
                         },
            onClick = { openMap(it.lat, it.lng) }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {

                vm.events.collect { list ->
                    val updated = list.map {
                        val distance = distanceKm(userLat, userLng, it.lat, it.lng)
                        it.copy(distance = distance)
                    }
                    adapter.submitList(updated)
                }
            }




        lifecycleScope.launch {
//
            vm.bookmarkedIds.collect { ids ->
                adapter.setBookmarkedIds(ids)
                Log.d("//////","isBookmarked"+ids)}
        }

    }
    private fun openMap(lat: Double, lng: Double) {
        val uri = Uri.parse("geo:$lat,$lng?q=$lat,$lng")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
    private fun requestLocationPermission() {
        val finePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (finePermission == PackageManager.PERMISSION_GRANTED) {
            getUserLocation()
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (fineLocationGranted || coarseLocationGranted) {
                // Permission granted → start location logic
                getUserLocation()
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->

                if (location != null) {
                    userLat = location.latitude
                    userLng = location.longitude
                    vm.setLocation(userLat!!, userLng!!)
//                    lifecycleScope.launch {
//                        vm.location.collect { (lat,lng)-> }
//                    }

                }
            }
    }
}