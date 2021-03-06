package workshop.akbolatss.xchangesrates.utils.extension

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import workshop.akbolatss.xchangesrates.domain.model.Snapshot

fun Context?.createNotificationChannel(snapshot: Snapshot) {
    this?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = snapshot.generateSnapshotName()
            val serviceChannel = NotificationChannel(
                channelId,
                channelId,
                NotificationManager.IMPORTANCE_MIN
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            val notificationChannel = notificationManager?.getNotificationChannel(channelId)
            if (notificationChannel == null)
                notificationManager?.createNotificationChannel(serviceChannel)
        }
    }
}

fun Context?.deleteNotificationChannel(snapshot: Snapshot) {
    this?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.deleteNotificationChannel(snapshot.generateSnapshotName())
        }
    }
}

fun Context?.clearOnGoingNotification(snapshot: Snapshot) {
    this?.let {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.cancel(snapshot.id.toInt())

    }
}
