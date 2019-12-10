package com.example.admin.personalproject4;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicActivity extends AppCompatActivity {
    List<SongInfo> listsong;
    ArrayList<String> songNameList = new ArrayList<String>();

    private CircleImageView circleImageView;//圆形组件
    private TextView tv_end;
    private TextView tv_song;
    private TextView tv_singer;
    private String listID;
    String path;
    String presongPath;
    String nextsongPath;
    String songname;
    String singer;
    String songtime;
    private Timer timer;
    private boolean isSeekBarChanging;//防止与计时器冲突
    private SeekBar seekBar;
    MediaPlayer mediaPlayer = new MediaPlayer();

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
        Utils.getmusic(MusicActivity.this);
        listsong = new ArrayList<>();
        listsong = Utils.getmusic(this);
        tv_end = findViewById(R.id.tv_end);
        tv_song = findViewById(R.id.song);
        tv_singer = findViewById(R.id.singer);
        seekBar = findViewById(R.id.bar);
        seekBar.setOnSeekBarChangeListener(new MySeekBar());
        Intent intent = getIntent();
        listID = intent.getStringExtra("listId");
        Log.d("TAG", "onCreate:ID IS ---------------> " + listID);
        path = intent.getStringExtra("path");
        presongPath = intent.getStringExtra("presongpath");
        nextsongPath = intent.getStringExtra("nextsongpath");
        songname = intent.getStringExtra("songname");
        singer = intent.getStringExtra("singer");
        songtime = intent.getStringExtra("songtime");
//
//        Log.d("TAG", "SONGNAME IS -------------------> "+songname);
//        Log.d("TAG", "Singer IS -------------------> "+singer);

        initPlayer();
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MusicActivity.this, MainActivity.class);
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
                Log.d("TAG", "playyyyyyyyyy:TIME I---------------> " + ShowTime(mediaPlayer.getDuration()) + "========123==" + mediaPlayer.getDuration());
            }
        });//播放
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        });//暂停
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPlayer();
                play(presongPath);

            }//上一首
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(nextsongPath);
            }
        });//下一首

    }


    public void initPlayer() {
        seekBar.setMax(Integer.parseInt(songtime));
        songtime = ShowTime(Integer.parseInt(songtime));
        Log.d("TAG", "initPlayer:TIME I---------------> " + songtime);
        tv_end.setText(songtime);
        tv_song.setText(singer);
        tv_singer.setText(songname);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isSeekBarChanging && mediaPlayer.isPlaying()) {//如果进度条未改变，并且当前正在播放
                    //tv1.append(""+mediaPlayer.getCurrentPosition());
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    //lrcShow(currentTime);
                    Message msg = new Message();
                    msg.what = 1;
//                    handler.sendMessage(msg);
                }
            }
        }, 0, 1000);
    }


    public String ShowTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        Log.d("TAG", "Minute---------------> " + minute);
        Log.d("TAG", "Second---------------> " + second);
        return String.format("%02d:%02d", minute, second);
    }//格式化时间





        public class MySeekBar implements SeekBar.OnSeekBarChangeListener {
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
            }

            /*滚动时,应当暂停后台定时器*/
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBarChanging = true;
            }

            /*滑动结束后，重新设置值*/
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekBarChanging = false;
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        }


        public void play (String path){
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaP) {
                        mediaP.start();
                    }
                });


            } catch (Exception e) {
                Log.v("MusicService", e.getMessage());
            }

        }
    }

