package com.example.mynotificationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mynotificationapp.job_scheduler.MainJobSchedulerActivity
import com.example.mynotificationapp.notification.MainNotificationActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun onNavigationClicked(v: View){
        startActivity(Intent(this, MainNotificationActivity::class.java))
    }

    fun onJobSchedulerClicked(v:View){
        startActivity(Intent(this, MainJobSchedulerActivity::class.java))
    }
}
