package com.example.medidordeimc.monitor

import android.app.NotificationManager
import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.medidordeimc.model.IMC
import com.example.medidordeimc.model.User

import java.util.concurrent.TimeUnit

class ForecastMonitor (context: Context) {
    private val wm = WorkManager.getInstance(context)
    private val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun updateInterval(user: User, isMonitored2w: Boolean = false, isMonitored4w: Boolean = false, isMonitored6w: Boolean = false) {
        //cancelCity(user)

        if (!isMonitored2w) return;
        val inputData = Data.Builder().putString("name", user.name).build()
        val request = PeriodicWorkRequestBuilder<ForecastWorker>(
            repeatInterval = 14, repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setInputData(inputData).build()
        wm.enqueueUniquePeriodicWork(
            user.name,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, request
        )

        if (!isMonitored4w) return;
        val inputData2 = Data.Builder().putString("name", user.name).build()
        val request2 = PeriodicWorkRequestBuilder<ForecastWorker>(
            repeatInterval = 28, repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setInputData(inputData2).build()
        wm.enqueueUniquePeriodicWork(
            user.name,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, request2
        )

        if (!isMonitored6w) return;
        val inputData3 = Data.Builder().putString("name", user.name).build()
        val request3 = PeriodicWorkRequestBuilder<ForecastWorker>(
            repeatInterval = 28, repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setInputData(inputData3).build()
        wm.enqueueUniquePeriodicWork(
            user.name,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, request3
        )
    }

    fun cancelCity(user: User ) {
        wm.cancelUniqueWork(user.name)
        nm.cancel(user.name.hashCode())
    }
    fun cancelAll() {
        wm.cancelAllWork()
        nm.cancelAll()
    }


}