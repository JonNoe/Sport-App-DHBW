package com.dhbw.dhbwsportapp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat



class BroadcastReceiver : BroadcastReceiver() {
    @SuppressLint("NotificationPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannelId = "default_channel"
        val notificationTitle = "Tägliche Erinnerung"
        val notificationText = "Es ist Zeit für dein tägliches Training!"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, notificationChannelId)
            .apply {
                setContentTitle(notificationTitle)
                setContentText(notificationText)
                setSmallIcon(R.drawable.dhbw_circle_24)
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }
            .build()

        notificationManager.notify(1, notification)
    }
}