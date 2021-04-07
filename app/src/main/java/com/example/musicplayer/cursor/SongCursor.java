package com.example.musicplayer.cursor;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.musicplayer.R;

public class SongCursor extends CursorAdapter {
    private TextView songName,singer;
    public SongCursor(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.song_list_item,
                parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
songName=view.findViewById(R.id.song_name);
singer=view.findViewById(R.id.song_singer);
songName.setText(cursor.getString(cursor.getColumnIndexOrThrow(
        MediaStore.Audio.Media.DISPLAY_NAME
)));
singer.setText(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore
.Audio.Media.ARTIST)));

    }
}
