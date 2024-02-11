package com.example.background_task;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PrayerTimeCheckerReceiver extends BroadcastReceiver {

    private SharedPreferences namajPreferences;
    private static final int NOTIFICATION_ID = 101;
    private static final String CHANNEL_ID = "PrayerReminderChannel";
    private static final String CHANNEL_NAME = "Prayer Reminder";

    @Override
    public void onReceive(Context context, Intent intent) {
        namajPreferences = context.getSharedPreferences("NamajPreferences", Context.MODE_PRIVATE);
        checkPrayerTimes(context);
    }

    private void checkPrayerTimes(Context context) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = sdf.format(calendar.getTime());

        // Example: Check if it's time for Fajr prayer
        String fajrStartTime = namajPreferences.getString("startTime_ফজর", "");
        String fajrFinishTime = namajPreferences.getString("finishTime_ফজর", "");

        if (!fajrStartTime.isEmpty() && !fajrFinishTime.isEmpty()) {
            try {
                Date currentTimeDate = sdf.parse(currentTime);
                Date fajrStartTimeDate = sdf.parse(fajrStartTime);
                Date fajrFinishTimeDate = sdf.parse(fajrFinishTime);

                if (currentTimeDate.after(fajrStartTimeDate) && currentTimeDate.before(fajrFinishTimeDate)) {
                    // It's time for Fajr prayer
                    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    showNotification(context, "Fajr prayer time. Device set to silent mode.");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Repeat the above logic for other prayers...
    }

    private void showNotification(Context context, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Prayer Reminder")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
