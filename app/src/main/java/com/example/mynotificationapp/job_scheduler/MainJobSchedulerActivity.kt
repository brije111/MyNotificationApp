package com.example.mynotificationapp.job_scheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.mynotificationapp.R
import kotlinx.android.synthetic.main.activity_main_job_scheduler.*

class MainJobSchedulerActivity : AppCompatActivity() {

    var selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE
    lateinit var jobScheduler: JobScheduler
    private val JOB_ID = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_job_scheduler)

        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val serviceName = ComponentName(this, NotificationJobService::class.java)
        val builder = JobInfo.Builder(JOB_ID, serviceName).apply {
            setRequiredNetworkType(selectedNetworkOption)
        }

        //scheduleJob(builder)

        radioGroup3.setOnCheckedChangeListener(fun(a, b) {
            when (b) {
                R.id.radioButton5 -> selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE
                R.id.radioButton6 -> selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY
                R.id.radioButton7 -> selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED
                else -> selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE
            }
            scheduleJob(builder)
            Toast.makeText(
                this, "check changed : " +
                        a.findViewById<RadioButton>(b).isChecked, Toast.LENGTH_SHORT
            ).show()
        })
    }

    private fun scheduleJob(builder: JobInfo.Builder) {
        val constraintSet = selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE
        if (constraintSet) {
            val jobInfo = builder.build()
            jobScheduler.schedule(jobInfo)
            Toast.makeText(
                this,
                "Job Scheduled, job will run when constraint are met",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(this, "Please set atleast one constraint", Toast.LENGTH_SHORT).show()
        }
    }
}
