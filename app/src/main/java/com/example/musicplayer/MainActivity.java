package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
private ImageView slashImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slashImage=findViewById(R.id.slash_image);
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
startActivity(new Intent(MainActivity.this, SongListActivity.class));
finish();
            }
        };
        Handler handler=new Handler();
        handler.postDelayed(runnable,3000);
    }
}