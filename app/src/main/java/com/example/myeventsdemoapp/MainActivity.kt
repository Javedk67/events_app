package com.example.myeventsdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.myeventsdemoapp.databinding.ActivityMainBinding
import com.example.myeventsdemoapp.ui.main.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: EventViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set content view
        setContentView(binding.root)


        // Observe FIRST (best practice)
        vm.events.observe(this) { list ->
            binding.textView.text = "Events: ${list.size}"
        }

        vm.loadEvents()
    }
}