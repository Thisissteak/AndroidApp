package com.example.admin.personalproject4;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private int listID;
    private boolean PlayModel;
    String path;
    String songname;
    String singer;
    String songtime;
    private boolean isSeekBarChanging;//防止与计时器冲突
    private SeekBar seekBar;
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ImageView file = findViewById(R.id.file);
        circleImageView = findViewById(R.id.circle_image);
//        final ImageView play = findViewById(R.id.play);
        ImageView stop = findViewById(R.id.stop);
        ImageView prev = findViewById(R.id.prev);
        ImageView next = findViewById(R.id.next);
        ImageView model = findViewById(R.id.model);
        ImageView random = findViewById(R.id.random);
        Utils.getmusic(MusicActivity.this);
        listsong = new ArrayList<>();
        listsong = Utils.getmusic(this);
        tv_end = findViewById(R.id.tv_end);
        tv_song = findViewById(R.id.song);
        tv_singer = findViewById(R.id.singer);
        seekBar = findViewById(R.id.bar);
        seekBar.setOnSeekBarChangeListener(new MySeekBar());
        Intent intent = getIntent();
        listID = intent.getIntExtra("listID",1);
        Log.d("TAG", "onCreate: ID IS ---------------> " + listID);
        initPlayer(listID);
        play(listID);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playrandom();
                Toast.makeText(MusicActivity.this,"随机播放",Toast.LENGTH_SHORT).show();
            }
        });
        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playloop();
                Toast.makeText(MusicActivity.this,"顺序播放",Toast.LENGTH_SHORT).show();
            }
        });
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
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                play(listID);
//                Log.d("TAG", "playyyyyyyyyy:TIME I---------------> " + ShowTime(mediaPlayer.getDuration()) + "========123==" + mediaPlayer.getDuration());
//            }
//        });//播放
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
                listID = listID-1;
                initPlayer(listID);
                play(listID);

            }//上一首
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listID = listID+1;
                initPlayer(listID);
                play(listID);
            }
        });//下一首
    }


    public void initPlayer(int listID) {
        Log.d("TAG", "initPlayer:listID is "+listID);
        songtime = listsong.get(listID).songTime;
        singer = listsong.get(listID).singer;
        songname = listsong.get(listID).songName;
        seekBar.setMax(Integer.parseInt(songtime));
        songtime = ShowTime(Integer.parseInt(songtime));
        Log.d("TAG", "initPlayer:TIME I---------------> " + songtime);
        tv_end.setText(songtime);
        tv_song.setText(singer);
        tv_singer.setText(songname);
        Timer timer = new Timer();
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
    private void playloop() {if (listID<listsong.size()){
        Log.d("TAG", "playloop: "+listID);
        listID++;
        Log.d("TAG", "playloop:2 "+listID);
        initPlayer(listID);
        play(listID);
    }
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



    private void playrandom(){
        listID = (int) (1 + Math.random() * (listsong.size() - 1 + 1));
        initPlayer(listID);
        play(listID);
    }


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
    private void single() {
        listID++;
        play(listID);
    }



    public void play (int i){
            try {
                mediaPlayer.reset();
                path = listsong.get(i).songPath;
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

