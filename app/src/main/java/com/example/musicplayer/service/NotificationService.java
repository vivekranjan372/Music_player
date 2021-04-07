package com.example.musicplayer.service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.musicplayer.R;

import java.util.Random;

public class NotificationService extends Service
{

    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    private String CHANNEL_ID="com.example.musicplayer.service";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
        


    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel  channel=new NotificationChannel(CHANNEL_ID,"Music Player",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Wel come to vivek music player");

channel.enableLights(true);
channel.setLightColor(Color.RED);
manager.createNotificationChannel(channel);


        }
        else
        {
            builder=new NotificationCompat.Builder(NotificationService.this,
                    CHANNEL_ID);
            builder.setContentTitle("Music Player").setSmallIcon(R.drawable.ic_music);
            builder.setContentText("wel come to vivek ranjan music player") ;
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(false);

        }
       builder.addAction(R.drawable.ic_notify_next,"",null);
        
        builder.setPriority(NotificationCompat.PRIORITY_LOW);
        manager.notify(new Random().nextInt(),builder.build());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;

    }

}
