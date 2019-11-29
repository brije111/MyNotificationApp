package com.example.mynotificationapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.mynotificationapp.R

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if (intent.action == "action_snooze")
            Toast.makeText(context, "broadcast received", Toast.LENGTH_SHORT).show()
        if (intent.action == "action_reply") {
            Toast.makeText(
                context,
                "broadcast received " + getMessageText(intent),
                Toast.LENGTH_SHORT
            ).show()

            val repliedNotification = NotificationCompat.Builder(context, "MY_CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_reply)
                .setContentText("reply success")
                .build()

            NotificationManagerCompat.from(context).apply {
                notify(0, repliedNotification)
            }
        }


    }
    private fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence("key_text_input")
    }
}
