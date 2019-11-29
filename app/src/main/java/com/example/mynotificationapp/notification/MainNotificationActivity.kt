package com.example.mynotificationapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.mynotificationapp.R

class MainNotificationActivity : AppCompatActivity() {

    private val CHANNEL_ID = "MY_CHANNEL_ID"
    private val ACTION_SNOOZE = "action_snooze"
    private val ACTION_REPLY = "action_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_notification)
        createNotificationChannel()
    }

    fun notifyMeBtnClick(v:View){
        //Toast.makeText(this, "Notify Me clicked", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainNotificationActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val actionIntent = Intent(this, MyReceiver::class.java).apply {
            action=ACTION_SNOOZE
            putExtra("notification_id", 0)
        }

        val actionIntentInput = Intent(this, MyReceiver::class.java).apply {
            action=ACTION_REPLY
            putExtra("notification_id", 0)
        }

        val KEY_TEXT_INPUT = "key_text_input"
        val remoteInput = RemoteInput.Builder(KEY_TEXT_INPUT).apply {
            setLabel("Reply Here...")
        }

        //val replyPendingIntent = PendingIntent.getBroadcast(applicationContext, 0,getMessacew)

        val actionPendingIntent = PendingIntent.getBroadcast(this, 0, actionIntent, 0)
        val actionPendingIntentReply = PendingIntent.getBroadcast(this, 0, actionIntentInput, 0)
        var action = NotificationCompat.Action.Builder(R.drawable.ic_reply, "reply", actionPendingIntentReply)
            .addRemoteInput(remoteInput.build()).build()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID).apply {
                setSmallIcon(R.mipmap.ic_launcher)
                setContentTitle("Dummy Title")
                setContentText("Dummy Text khfkkxcckxvnmcxnvj sijfkdn fdskjfs fdjksf")
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
                setContentIntent(pendingIntent)
                setAutoCancel(true)
                addAction(R.drawable.ic_snooze, "SNOOZE", actionPendingIntent)
                addAction(action)
        }

        with(NotificationManagerCompat.from(this)){
            notify(0, notification.build());
        }
    }

    fun updateMeBtnClick(v:View){
        Toast.makeText(this, "Update Me clicked", Toast.LENGTH_SHORT).show()
        val repliedNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_reply)
            .setContentText("update success")
            .build()

        NotificationManagerCompat.from(this).apply {
            notify(0, repliedNotification)
        }
    }

    fun cancelMeBtnClick(v:View){
        Toast.makeText(this, "Cancel Me clicked", Toast.LENGTH_SHORT).show()
        NotificationManagerCompat.from(this).cancel(0)
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, "my channel name", NotificationManager.IMPORTANCE_DEFAULT)
                .apply {
                description = "My notification channel description"
            }
            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
