package com.example.admin.personalproject4;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicActivity extends AppCompatActivity {
    private CircleImageView circleImageView;//圆形组件
    String path;
    String presongPath;
    String nextsongPath;
    MediaPlayer mediaPlayer=new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ImageView file = findViewById(R.id.file);
        circleImageView = findViewById(R.id.circle_image);
        ImageView play = findViewById(R.id.play);
        ImageView stop = findViewById(R.id.stop);
        ImageView prev = findViewById(R.id.prev);
        ImageView next = findViewById(R.id.next);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        presongPath = intent.getStringExtra("presongpath");
        nextsongPath = intent.getStringExtra("nextsongpath");
       file.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent1 = new Intent(MusicActivity.this,MainActivity.class);
               if (mediaPlayer.isPlaying()) {
                   mediaPlayer.pause();
               }
               startActivity(intent1);//启动list activity

               }
       });//回到文件列表
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    play(path);
            }
        });//播放
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }else{
                    mediaPlayer.start();
                }
            }
        });//暂停
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(presongPath);
            }
        });//上一首
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(nextsongPath);
            }
        });

    }
    public void play(String path){
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaP) {
                    mediaP.start();
                }
            });


        }catch (Exception e){
            Log.v("MusicService", e.getMessage());
        }

    }
}
