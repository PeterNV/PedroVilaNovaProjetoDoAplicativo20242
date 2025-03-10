package com.example.medidordeimc.monitor

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.medidordeimc.MainActivity
import com.example.medidordeimc.R


class ForecastWorker(context: Context, params: WorkerParameters) : Worker(context,
    params) {
    companion object {
            private const val CHANNEL_ID: String = "MEDIDOR_DE_IMC"
    }
    override fun doWork(): Result {
        val cityName = inputData.getString("name") ?: return Result.failure()
        showNotification(cityName)
        return Result.success()
    }
    private fun showNotification(userName: String) {
        val newIntent = Intent(this.applicationContext, MainActivity::class.java)
        newIntent.addFlags(
            Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP)
        newIntent.putExtra("name", userName)
        val pendingIntent = PendingIntent.getActivity(
            this.applicationContext, userName.hashCode(), newIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        createNotificationChannel()
        val builder = NotificationCompat
            .Builder(this.applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(userName)
            .setContentText("Clique para registrar um novo IMC.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val notificationManager: NotificationManager =
            this.applicationContext
                .getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
// ID = hashCode: para substituir ou remover notificações
        notificationManager.notify(userName.hashCode(), builder.build())
    }
    private fun createNotificationChannel() {
        val name = "MedidorDeIMC"
        val descriptionText = "MedidorDeIMC Notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
            .apply {
                description = descriptionText
            }
        val notificationManager: NotificationManager = this.applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}