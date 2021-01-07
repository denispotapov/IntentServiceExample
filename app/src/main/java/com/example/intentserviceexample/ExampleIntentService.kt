package com.example.intentserviceexample

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import timber.log.Timber

class ExampleIntentService : IntentService("ExampleIntentService") {

    private lateinit var wakeLock: PowerManager.WakeLock

    init {
        setIntentRedelivery(true)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")

        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "Example:WakeLock")
        wakeLock.acquire()
        Timber.d("wakelock acquired")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example IntentService")
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_android)
                .build()

            startForeground(1, notification)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        Timber.d("onHandleIntent")

        val input = intent?.getStringExtra("key")

        for (i in 0..10) {
            Timber.d("onHandleIntent: $input - $i")
            SystemClock.sleep(1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")

        wakeLock.release()
        Timber.d("wakelock released")
    }
}