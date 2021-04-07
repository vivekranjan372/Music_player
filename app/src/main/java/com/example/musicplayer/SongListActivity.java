package com.example.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.musicplayer.cursor.SongCursor;

import java.util.ArrayList;


public class SongListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
private ListView listView;
private ArrayList<String>songList;
private ArrayList<String>singerList;
private SongCursor songCursor;
private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        listView=findViewById(R.id.song_list);
        songList=new ArrayList<>();
        singerList=new ArrayList<>();
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission
        .READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
listView.setOnItemClickListener(this);

            getData();



}

    private void getData() {
     try {
         cursor=getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                 ,null,null,null,null);
         songCursor=new SongCursor(this,cursor,0);
         listView.setAdapter(songCursor);

         while(cursor.moveToNext())
         {
             songList.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio
                     .Media.DISPLAY_NAME)));
             singerList.add(cursor.getString(cursor.getColumnIndexOrThrow(
                     MediaStore.Audio.Media.DATA
             )));
         }

         songCursor.notifyDataSetChanged();
     }
     catch (SecurityException e)
     {
         e.printStackTrace();
     }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
Intent intent=new Intent(this,MusicPlayer.class);
        intent.putExtra("position",position);
        intent.putExtra("songData",singerList.get(position));
        intent.putExtra("getAllData",singerList);
startActivity(intent);

    }
}