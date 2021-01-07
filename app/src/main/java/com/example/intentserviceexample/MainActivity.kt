package com.example.intentserviceexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.intentserviceexample.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        binding.startService.setOnClickListener {

            val input = binding.editTextInput.text.toString()

            val serviceIntent = Intent(this, ExampleIntentService::class.java)
            serviceIntent.putExtra("key", input)
            ContextCompat.startForegroundService(this, serviceIntent)
        }
    }
}