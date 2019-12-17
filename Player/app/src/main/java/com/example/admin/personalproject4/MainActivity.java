package com.example.admin.personalproject4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 0;
    MediaPlayer mediaPlayer = new MediaPlayer();
    ListView listView;
    ArrayList<String> songNameList = new ArrayList<String>();
    List<com.example.admin.personalproject4.SongInfo> listsong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (!permissions.isEmpty()) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION);
            }

        }


        listsong = new ArrayList<>();
        listsong = Utils.getmusic(this);
        for (int i = 0; i < listsong.size(); i++) {
            songNameList.add(listsong.get(i).singer);
        }
        Log.d("TAG", "onCreate:listsong size is "+listsong.size());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, songNameList);
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnLongClickListener(new View.OnLongClickListener() {
                                            @Override
                                            public boolean onLongClick(View v) {
                                                Toast.makeText(MainActivity.this,"djfkldj",Toast.LENGTH_SHORT).show();
//                                                DeleteUseSql(strId);
//                                                setNotesListView(getAll());
                                                return true;
                                            }
                                        });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {//长按弹出菜单
                        int songid = (int) id;
                        Log.d("TAG", " LIST ID IS " + songid);
//                String a = Integer.toString(songid);
                        Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                        intent.putExtra("listID", songid);
                        startActivity(intent);


                    }
                });
    }
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo){
        getMenuInflater().inflate(R.menu.list_menu,menu);
        Log.d("TAG", "9999999999999999999999999999 ");
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Log.d("TAG", "8888888888888888888888888 ");
        int id = item.getItemId();
        Log.d("TAG", "77777777777777777 ");
        switch (id){
            case R.id.action_delete:
                Log.d("TAG", "666666666666666 ");
               songNameList.remove(id);
               ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, songNameList);
                listView.setAdapter(adapter);

        }
        return super.onOptionsItemSelected(item);

    }
//    private void DeleteUseSql(String strId){
//        String sql = "delete from notes where _id='" + strId + "'";
//        SQLiteDatabase db = notesDBHelper.getReadableDatabase();
//        db.execSQL(sql);
//    }


    //权限请求许可
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
                } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}