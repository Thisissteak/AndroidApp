package com.example.admin.personalproject4;

/**
 * Created by asus on 2018/12/3.
 */

public class SongInfo {
    public String songName;//歌曲名
    public String singer;//歌手
    public String sid;

    public String songPath;//歌曲地址
    public String songTime;
    public String getSongTime(){ return songTime; }
    public void setSongTime(String songTime){this.songTime=songTime;}
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }
}


