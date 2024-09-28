package com.example.run.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.core.domain.run.RunRepository

class FetchRunWorker(
    context: Context, params: WorkerParameters, private val runRepository: RunRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        println("FetchRunWorker: $runAttemptCount")

        setForegroundAsync(showNotification(context = this.applicationContext, "Emir", "emir java"))

        return when (val result = runRepository.fetchRuns()) {
            is com.example.core.domain.util.Result.Error -> result.error.toWorkerResult()
            is com.example.core.domain.util.Result.Success -> {
                Result.success()
            }
        }


    }
}

fun showNotification(context: Context, title: String, message: String): ForegroundInfo {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Create notification channel for Android O and higher
    val channelId = "work_manager_channel_id"
    val channel = NotificationChannel(
        channelId,
        "Work Manager Notifications",
        NotificationManager.IMPORTANCE_DEFAULT
    )
    notificationManager.createNotificationChannel(channel)

    // Build the notification
    val notification: Notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(R.drawable.logo) // Add your own app icon here
        .setOngoing(true)
        .build()

    // Show the notification
    notificationManager.notify(1, notification)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        ForegroundInfo(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
    } else {
        ForegroundInfo(1, notification)
    }
}