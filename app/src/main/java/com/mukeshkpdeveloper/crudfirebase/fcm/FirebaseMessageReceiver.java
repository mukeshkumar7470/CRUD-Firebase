package com.mukeshkpdeveloper.crudfirebase.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mukeshkpdeveloper.crudfirebase.R;
import com.mukeshkpdeveloper.crudfirebase.ui.MainActivity;

public class FirebaseMessageReceiver extends FirebaseMessagingService {

    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage) {

        /*if(remoteMessage.getData().size()>0){
            showNotification(remoteMessage.getData().get("title"),
                          remoteMessage.getData().get("message"));
        }*/


        if (remoteMessage.getNotification() != null) {
            showNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }

    private RemoteViews getCustomDesign(String title,
                                        String message) {
        RemoteViews remoteViews = new RemoteViews(
                getApplicationContext().getPackageName(),
                R.layout.notification);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
        remoteViews.setImageViewResource(R.id.icon,
                R.drawable.ic_app_icon_foreground);
        return remoteViews;
    }

    public void showNotification(String title,
                                 String message) {
        Intent intent
                = new Intent(this, MainActivity.class);
        String channel_id = "notification_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        /*NotificationCompat.Builder builder
                = new NotificationCompat
                .Builder(getApplicationContext(),
                channel_id)
                .setSmallIcon(R.drawable.ic_app_icon_foreground)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000,
                        1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);*/
        Uri sound = Uri. parse (ContentResolver. SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/app_src_main_res_raw_iphone_ringtone.mp3" ) ;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.drawable.ic_app_icon_foreground)
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setPriority(Notification.PRIORITY_MAX) //Important for heads-up notification
                .setContentIntent(pendingIntent);


        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.JELLY_BEAN) {
            builder = builder.setContent(
                    getCustomDesign(title, message));
        } else {
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_app_icon_foreground);
        }
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        /*if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel
                    = new NotificationChannel(
                    channel_id, "web_app",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(
                    notificationChannel);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Android App";
            String description = "Android App";
            int importance = NotificationManager.IMPORTANCE_HIGH; //Important for heads-up notification
            NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
            channel.setDescription(description);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build());
    }

}
