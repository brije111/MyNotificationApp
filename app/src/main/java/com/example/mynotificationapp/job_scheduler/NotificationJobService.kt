package com.example.mynotificationapp.job_scheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.mynotificationapp.R

class NotificationJobService:JobService() {

    lateinit var mNotifyManager: NotificationManager
    private val CHANNEL_ID = "channel_id"

    override fun onStopJob(p0: JobParameters?): Boolean {
        createNotificationChannel()

        val intent = Intent(this, MainJobSchedulerActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_sync)
            setContentTitle("Job Service")
            setContentText("Your job is running")
            setContentIntent(pIntent)
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setAutoCancel(true)
        }

        mNotifyManager.notify(0, notification.build())
        return false
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createNotificationChannel(){
        mNotifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //create notification channel with all parameter

            val notificationChannel = NotificationChannel(CHANNEL_ID, "Job Service Notification",
                NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "Notification from Job Service"
            }

            mNotifyManager.createNotificationChannel(notificationChannel)
        }
    }
}