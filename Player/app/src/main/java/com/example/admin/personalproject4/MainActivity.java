package com.example.admin.personalproject4;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.SidePropagation;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.personalproject4.MusicActivity;
import com.example.admin.personalproject4.R;
import com.example.admin.personalproject4.SongInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 0;
    MediaPlayer mediaPlayer=new MediaPlayer();
    ListView listView ;
    ArrayList<String> songNameList = new ArrayList<String>();
    List<com.example.admin.personalproject4.SongInfo> listsong;
    String path ;
    String prepath;
    String nextpath ;
    String songname;
    String singer;
    String songtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }else {
                //     preferencesUtility.setString("storage", "true");
            }
            if (hasReadPermission != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }else {
                // preferencesUtility.setString("storage", "true");
            }
            if (!permissions.isEmpty()){
                // requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},REQUEST_PERMISSION);
            }

        }




//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_READ_MEDIA);
//        }else{
//            readDataExternal();
//        }


/*
        String str[] = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE};
        //Cursor cursor = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,
               //null,null,MediaStore.Audio.AudioColumns.IS_MUSIC);
        Cursor cursor = this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        while (cursor.moveToNext()){
            String songID = cursor.getString(0);
            String songName = cursor.getString(1);
            String songPath = cursor.getString(2);
            String songSize = cursor.getString(3);
            arrayList.add(songPath);
            */
//            songNameList=new ArrayList<>();
        listsong = new ArrayList<>();
        listsong=Utils.getmusic(this);
        for(int i=0;i<listsong.size();i++){
            songNameList.add(listsong.get(i).singer);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, songNameList);
        listView = (ListView) findViewById(R.id.listview);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {//长按弹出菜单
                int songid = (int) id;
                Log.d("TAG", " LIST ID IS "+songid);
//                String a = Integer.toString(songid);
                Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                intent.putExtra("listID",songid);
////                intent.putExtra("listID",a);
//                Log.d("TAG", "long id is==============> "+id);
//                if(position>0 && position<listsong.size()-1) {
//                    path = listsong.get(songid).songPath;
//                    prepath = listsong.get(songid - 1).songPath;
//                    nextpath = listsong.get(songid + 1).songPath;
//                    songname = listsong.get(songid).songName;
//                    singer = listsong.get(songid).singer;
//                    songtime =listsong.get(songid).songTime;
//                    Log.d("TAG", "onItemClick:songname----------> "+songname);
//                    Log.d("TAG", "onItemClick:songtime----------> "+songtime);
//
//
//                    intent.putExtra("path", path);
//                    intent.putExtra("presongpath", prepath);
//                    intent.putExtra("nextsongpath", nextpath);
//                    intent.putExtra("songname",songname);
//                    intent.putExtra("singer",singer);
//                    intent.putExtra("songtime",songtime);
//
//                }
//                else if(position==0)
//                {
//                    path = listsong.get(songid).songPath;
//                    prepath = listsong.get(songid+listsong.size()-1).songPath;
//                    nextpath = listsong.get(songid + 1).songPath;
//                    intent.putExtra("path", path);
//                    intent.putExtra("presongpath", prepath);
//                    intent.putExtra("nextsongpath", nextpath);
//                }
//                else {
//                    path = listsong.get(songid).songPath;
//                    prepath = listsong.get(songid - 1).songPath;
//                    nextpath = listsong.get(songid-listsong.size()+1).songPath;
//                    intent.putExtra("path", path);
//                    intent.putExtra("presongpath", prepath);
//                    intent.putExtra("nextsongpath", nextpath);
//
//                }
                startActivity(intent);


            }
        });
    }



    //权限请求许可
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case REQUEST_PERMISSION:{
                for (int i = 0; i < permissions.length; i++){
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
                    }else if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
                    }
                }
            }break;
            default:{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    //播放
//            public void play(String path){
//            try{
////                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
////                int songid = (int) info.id;
////                String path = arrayList.get(songid).toString();
//                mediaPlayer.reset();
////                String datapath = arrayList.get(songid);
//                mediaPlayer.setDataSource(path);
//                mediaPlayer.prepareAsync();
//                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mediaP) {
//                        mediaP.start();
//                    }
//                });
//
//
//            }catch (Exception e){
//                Log.v("MusicService", e.getMessage());
//                }
//
//            }
}





//    class songFile{
//        Context context ;
//        public List<SongInfo> songlist = new ArrayList<>();
//         Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,
//                 null,null,MediaStore.Audio.AudioColumns.IS_MUSIC);
//
//    }

class Utils {
    //定义一个集合，存放从本地读取到的内容
//    public static ArrayList<String> list=new ArrayList<>();
    public static List<SongInfo> listsong;
    public static SongInfo songInfo;

    public static List<SongInfo> getmusic(Context context) {

        listsong = new ArrayList<>();
/***************************************/
        //另一种扫描方法
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                        BaseColumns._ID,
                        MediaStore.Audio.AudioColumns.IS_MUSIC,
                        MediaStore.Audio.AudioColumns.TITLE,
                        MediaStore.Audio.AudioColumns.ARTIST,
                        MediaStore.Audio.AudioColumns.ALBUM,
                        MediaStore.Audio.AudioColumns.ALBUM_ID,
                        MediaStore.Audio.AudioColumns.DATA,
                        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
                        MediaStore.Audio.AudioColumns.SIZE,
                        MediaStore.Audio.AudioColumns.DURATION
                },
                null,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                songInfo = new SongInfo();
                songInfo.sid=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                songInfo.songName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                songInfo.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                songInfo.songPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                songInfo.songTime = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//                Log.d("TAG", "IDIS--------------> "+songInfo.sid);
//                Log.d("TAG", "tIME--------------> "+songInfo.songTime);
               if(songInfo.songName.contains("-") )
                {
                    String[] str = songInfo.songName.split("-");
                    songInfo.singer=str[0];
                    songInfo.songName=str[1];
//                    Log.d("TAG", "STR0 IS--------------> "+str[0]);
//                    Log.d("TAG", "STR1 IS--------------> "+str[1]);
                }
                listsong.add(songInfo);
            }
        }

        cursor.close();
        return listsong;
    }


}
