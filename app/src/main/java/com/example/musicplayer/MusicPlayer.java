package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicplayer.service.MusicServiceBackend;
import com.example.musicplayer.service.NotificationService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicPlayer extends AppCompatActivity implements
        View.OnClickListener, MediaPlayer.OnCompletionListener {
private Toolbar toolbar;
private TextView currentPosition,maxPosition,shuffleText;
private ImageView previous,play,next,songIcon,repeat,like;
private SeekBar seekBar;
private ArrayList<String>songList;
   private  int getPosition;
   private  String getSongData;
   private MediaPlayer mediaPlayer;
   private boolean isRunning,isShuffle,isRepeat=false;
private LinearLayout shuffleLayout;
private SharedPreferences preferences;
private int intPreference;
private SharedPreferences.Editor editor;
private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        toolbar=findViewById(R.id.tool_bar);

        currentPosition=findViewById(R.id.current_position);
        maxPosition=findViewById(R.id.max_position);
        previous=findViewById(R.id.previous);
        play=findViewById(R.id.play);
        next=findViewById(R.id.next);
        songIcon=findViewById(R.id.song_icon);
        seekBar=findViewById(R.id.seek_bar);
       shuffleLayout=findViewById(R.id.shuffle_layout);
       shuffleText=findViewById(R.id.shuffle_text);
        repeat=findViewById(R.id.repeat);
        like=findViewById(R.id.like);
        songList=new ArrayList<>();
        play.setOnClickListener(this);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        repeat.setOnClickListener(this);
       shuffleLayout.setOnClickListener(this);
        like.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Music Player");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(Color.RED);
        toolbar.setBackgroundColor(Color.WHITE);
        preferences=getSharedPreferences("songPreference",MODE_PRIVATE);

        Bundle bundle=getIntent().getExtras();
       if(bundle!=null)
       {
            getPosition=bundle.getInt("position");
           getSongData=bundle.getString("songData");
           songList=bundle.getStringArrayList("getAllData");
       }



//
     mediaPlayer=MediaPlayer.create(this, Uri.parse(getSongData));
//
       mediaPlayer.start();
       play.setImageResource(R.drawable.ic_pause);
//        editor=preferences.edit();
//        editor.putInt("songPosition",getPosition).apply();



//        MediaMetadataRetriever metadataRetriever=new MediaMetadataRetriever();
//        metadataRetriever.setDataSource(getSongData);
//        byte []bytes=metadataRetriever.getEmbeddedPicture();
//        if(bytes!=null)
//        {
//            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            songIcon.setImageBitmap(bitmap);
//        }
//        else
//        {
//            songIcon.setImageResource(R.drawable.ic_music);
//        }

        runSong();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                {

                    mediaPlayer.seekTo(progress);
                }
                currentPosition.setText(
                        convertToDuration(mediaPlayer.getCurrentPosition()));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void onStart() {
intent=new Intent(this,MusicServiceBackend.class);
intent.putExtra("isPlaying",mediaPlayer.isPlaying());
intent.putExtra("songList",songList);
intent.putExtra("position",getPosition);
//startService(intent);


        super.onStart();

    }

    @Override
    protected void onStop() {
        Intent notification=new Intent(this, NotificationService.class);
        startService(notification);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Intent notification=new Intent(this, NotificationService.class);
        startService(notification);
        super.onDestroy();

    }

    private void runSong() {
        maxPosition.setText(convertToDuration(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
        new Thread()
        {
            public void run()
            {

                while (mediaPlayer.getCurrentPosition()!=mediaPlayer.getDuration())
                {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    mediaPlayer.setOnCompletionListener(MusicPlayer.this);

                }

            }
        }.start();
    }
    private String convertToDuration(long duration)
    {
        String d="";
        duration=duration/1000;
        d=d+duration/60+":"+duration%60;
        return d;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.play:
            {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.ic_play);
                }
                else
                {
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.ic_pause);
                }
                break;
            }
            case R.id.previous:
            {
                if(getPosition-1!=-1)
                {
                    mediaPlayer.stop();
                    play.setImageResource(R.drawable.ic_play);
                    mediaPlayer=MediaPlayer.
                            create(MusicPlayer.this,
                                    Uri.parse(songList.get(getPosition-1)));
                    runSong();
                    mediaPlayer.start();
                    getPosition=getPosition-1;
                    play.setImageResource(R.drawable.ic_pause);
                }
                else
                {
                    mediaPlayer.stop();
                    play.setImageResource(R.drawable.ic_play);
                    mediaPlayer=MediaPlayer.
                            create(MusicPlayer.this,
                                    Uri.parse(songList.get(songList.size()-1)));
                    runSong();
                    mediaPlayer.start();
                    getPosition=songList.size()-1;
                    play.setImageResource(R.drawable.ic_pause);
                }

                break;
            }
            case R.id.next:
            {
                if(getPosition+1==songList.size())
                {

                    mediaPlayer.stop();
                    play.setImageResource(R.drawable.ic_play);
                    mediaPlayer=MediaPlayer.create(
                            MusicPlayer.this,Uri.parse(songList.get(0)));
                    runSong();
                    mediaPlayer.start();
                    getPosition=0;
                    play.setImageResource(R.drawable.ic_pause);
                }
                else
                {

                    mediaPlayer.stop();
                    play.setImageResource(R.drawable.ic_play);
                    mediaPlayer=MediaPlayer.create(
                            MusicPlayer.this,
                            Uri.parse(songList.get(getPosition+1)));
                    runSong();
                    mediaPlayer.start();
                    getPosition=getPosition+1;
                    play.setImageResource(R.drawable.ic_pause);
                }
                break;
            }
            case R.id.shuffle_layout:
            {
               if(isShuffle)
               {
                   shuffleText.setVisibility(View.GONE);

                isShuffle=false;
               }
               else {

                   shuffleText.setVisibility(View.VISIBLE);
                   isRepeat=false;
                   repeat.setImageResource(R.drawable.ic_repeat);
                   isShuffle=true;

               }

                break;
            }
            case R.id.repeat:
            {
                if(isRepeat)
                {
                  repeat.setImageResource(R.drawable.ic_repeat);

                    isRepeat=false;
                }
                else {
                    repeat.setImageResource(R.drawable.ic_repeat_one);
isShuffle=false;
                    shuffleText.setVisibility(View.GONE);
                    isRepeat=true;

                }
                break;
            }
            case R.id.like:
            {
                if(isRunning)
                {
                    like.setImageResource(R.drawable.ic_like);
                    isRunning=false;

                }
                else {
                    like.setImageResource(R.drawable.ic_favourite);
                    isRunning=true;

                }
                break;
            }
        }

    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d("tag","finished");
        mp.stop();
        play.setImageResource(R.drawable.ic_play);
        if(isShuffle)
        {
            Random random=new Random();
            getPosition=random.nextInt(songList.size());
            whichSong();
        }
        else if(isRepeat)
        {
          whichSong();
        }
          else
        {
            getPosition=getPosition+1;
            whichSong();
        }


    }
    private void whichSong()
    {
        mediaPlayer=MediaPlayer.create(MusicPlayer.this,Uri.parse(songList.get(getPosition)));
        runSong();
        mediaPlayer.start();
        editor=preferences.edit();
        editor.putInt("songPosition",getPosition).apply();
        play.setImageResource(R.drawable.ic_pause);
    }
}