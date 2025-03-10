import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crie um canal de notificação (necessário para Android 8.0 e superior)
        val channelId = "imc_reminder_channel"
        val channelName = "IMC Reminder"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)

        // Crie a notificação
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Lembrete de IMC")
            .setContentText("É hora de verificar seu IMC novamente!")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Exiba a notificação
        notificationManager.notify(1, notification)

        return Result.success()
    }
}