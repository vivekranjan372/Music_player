package com.example.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MusicServiceBackend extends Service {
    private ArrayList<String>songList;
    private MediaPlayer mediaPlayer;
    private int songPosition;
    private boolean isPlaying;

    @Override
    public void onCreate() {
        super.onCreate();
        songList=new ArrayList<>();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("tag"," on start service");
        songList=intent.getStringArrayListExtra("songList");
        songPosition=intent.getIntExtra("position",-321);
        isPlaying=intent.getExtras().getBoolean("isPlaying");

        if(isPlaying)
        {

            Log.d("tag "," song is started");
        }
        else
        {mediaPlayer.start();
            Log.d("tag "," song is stopped");
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
