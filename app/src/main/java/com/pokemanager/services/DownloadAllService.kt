package com.pokemanager.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.pokemanager.use_cases.DownloadAllUseCase
import com.pokemanager.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.pokemanager.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.pokemanager.utils.Constants.NOTIFICATION_ID
import com.pokemanager.utils.Constants.SERVICE_ACTION_START
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DownloadAllService : LifecycleService() {

    var isFirstRun = true

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder
    lateinit var curNotificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var downloadAllUseCase: DownloadAllUseCase

    companion object {
        val progress = MutableLiveData<Int>()
        val total = MutableLiveData<Int>()
    }

    override fun onCreate() {
        super.onCreate()
        curNotificationBuilder = baseNotificationBuilder
        postInitialValues()
    }

    private fun postInitialValues() {
        total.postValue(3)
        progress.postValue(0)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(it.action) {
                SERVICE_ACTION_START -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForegroundService() {
        startDownload()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())

        progress.observe(this) {
            val notification = curNotificationBuilder
                .setContentText("${progress.value} de ${total.value}")
            notificationManager.notify(NOTIFICATION_ID, notification.build())
        }
    }

    private fun startDownload() {
        CoroutineScope(Dispatchers.IO).launch {
            downloadAllUseCase().collectLatest {
                Log.d("DownloadAllService", it.toString())
                progress.postValue(it.step)
                if (it.isFinished) {
                    stopSelf()
                    Log.d("DownloadAllService", "isFinished")
                    val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                    mNotificationManager.cancel(NOTIFICATION_ID)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}