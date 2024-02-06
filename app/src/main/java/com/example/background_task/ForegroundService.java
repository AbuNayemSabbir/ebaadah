package com.example.background_task;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ForegroundService extends Service {
    private static final String TAG = ForegroundService.class.getName();
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final long DELAYED_TIME = 1000;
    private static final long INTERVAL = 1 * 1000; // 2 minutes in milliseconds

    int counter = 0;

    private final Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);

        // Modify to include FLAG_IMMUTABLE or FLAG_MUTABLE
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        }

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("sssss")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);

        // Set up the timer for sound and vibration activation
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, 0);
                notification.setContentText("Sound activated!");
                startForeground(1, notification.build());
            }
        }, INTERVAL);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                notification.setContentText("Vibration activated!");
                startForeground(1, notification.build());
            }
        }, 2 * INTERVAL);

        // Start the periodic notification update
        startPeriodicNotification(notification);

        return START_NOT_STICKY;
    }

    private void startPeriodicNotification(final NotificationCompat.Builder notification) {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(DELAYED_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Update notification content text
                    counter = counter + 1;
                    notification.setContentText("Counter: " + counter);
                    startForeground(1, notification.build());
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove callbacks to prevent memory leaks
        handler.removeCallbacksAndMessages(null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            serviceChannel.enableVibration(false);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
