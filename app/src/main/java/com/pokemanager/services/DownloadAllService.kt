package com.pokemanager.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.pokemanager.R
import com.pokemanager.use_cases.DownloadAllUseCase
import com.pokemanager.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.pokemanager.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.pokemanager.utils.Constants.NOTIFICATION_ID
import com.pokemanager.utils.Constants.SERVICE_ACTION_START
import com.pokemanager.utils.Utils
import com.pokemanager.utils.VisualUtils
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

    lateinit var notificationManager: NotificationManager

    companion object {
        var total = 0
        val progress = MutableLiveData<Int>()
    }

    override fun onCreate() {
        super.onCreate()
        curNotificationBuilder = baseNotificationBuilder
        postInitialValues()
    }

    private fun postInitialValues() {
        total = Utils.getTotalStepsAtDownloadingAll()
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
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())

        progress.observe(this, progressObserver)
    }

    private val progressObserver = Observer<Int> { value ->
        Log.d("DownloadAllService", "Update $value")
        curNotificationBuilder.setProgress(total, value, false)
        val notificationLayout = getNotificationView(value, total)
        val notification = curNotificationBuilder.setCustomContentView(notificationLayout)
        if (total == value) {
            notification
                .setContentText(getString(R.string.download_all_notification_done_text))
                .setProgress(0, 0, false)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

    private fun getNotificationView(value: Int, total: Int): RemoteViews {
        val percentage = VisualUtils.getDownloadPercentage(
            value,
            total,
            getString(R.string.global_percentage_symbol),
            getString(R.string.setUp_downloading_tv_progress_done)
        )
        return RemoteViews(packageName, R.layout.notification_small).apply {
            setTextViewText(R.id.tv_progress, percentage)
            setProgressBar(R.id.pb_downloading, total, value, false)
        }
    }

    private fun startDownload() {
        CoroutineScope(Dispatchers.IO).launch {
            downloadAllUseCase().collectLatest {
                Log.d("DownloadAllService", it.toString())
                progress.postValue(it.step)
                if (it.isFinished) {
                    Log.d("DownloadAllService", "isFinished")
                    killService()
                }
            }
        }
    }

    private fun killService() {
        isFirstRun = true
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("DownloadAllService", "Observer Removed")
            progress.removeObserver(progressObserver)
            //postInitialValues()
        }
        stopForeground()
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.cancel(NOTIFICATION_ID)
        stopSelf()
    }

    private fun stopForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
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