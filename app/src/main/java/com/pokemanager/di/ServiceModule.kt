package com.pokemanager.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.pokemanager.R
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.data.repositories.MainRepository
import com.pokemanager.ui.MainActivity
import com.pokemanager.use_cases.DownloadAllUseCase
import com.pokemanager.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    fun provideBaseNotificationBuilder(
        @ApplicationContext app: Context,
        pendingIntent: PendingIntent
    ) : NotificationCompat.Builder {

        val notificationLayout = RemoteViews(app.packageName, R.layout.notification_small)

        return NotificationCompat.Builder(app, Constants.NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setOngoing(false)
            .setSmallIcon(R.drawable.ic_empty)
            .setContent(notificationLayout)
            .setColor(ContextCompat.getColor(app, R.color.red))
            .setColorized(true)
            .setContentIntent(pendingIntent)
            /*.setPriority(NotificationCompat.PRIORITY_HIGH)//
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setContentTitle(app.getString(R.string.app_name_one_line))
            .setContentText(app.getString(R.string.download_all_notification_text))*/
    }

    @ServiceScoped
    @Provides
    fun provideMainActivityPendingIntent(
        @ApplicationContext app: Context
    ): PendingIntent = PendingIntent.getActivity(
        app,
        0,
        Intent(app, MainActivity::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    @ServiceScoped
    @Provides
    fun getDownloadAllUseCase(
        mainRepository: MainRepository,
        pokeManagerPreferences: PokeManagerPreferences
    ): DownloadAllUseCase {
        return DownloadAllUseCase(mainRepository, pokeManagerPreferences)
    }
}