package com.example.contentest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_query= findViewById(R.id.bt_query);
        Button bt_add = findViewById(R.id.bt_add);
        Button bt_delete = findViewById(R.id.bt_delete);
        Button bt_update = findViewById(R.id.bt_update);
        bt_query.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.notepads.provider");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                Log.d("TAG", "IF------>ERROR ");
                if (cursor!=null){
                    while (cursor.moveToNext()){
                    String title =cursor.getString(cursor.getColumnIndex("title"));
                    String context =cursor.getString(cursor.getColumnIndex("context"));
                    String time =cursor.getString(cursor.getColumnIndex("time"));
                    Log.d("MainActivity", "TITIE:"+title);
                    Log.d("MainActivity", "CONTEXT::"+context);
                    Log.d("MainActivity", "TIME::"+time);
                    }
                }else {
                    Log.d("TAG", "ELSE------>ERROR ");
                }
                cursor.close();
            }
        });
        bt_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.notepads.provider");
                ContentValues contentValues = new ContentValues();
                contentValues.put("title","adapt");
                contentValues.put("context","this is adapt");
                contentValues.put("time","8.8");
                Uri newUri = getContentResolver().insert(uri,contentValues);
//                assert newUri != null;
                newId = newUri.getPathSegments().get(1);
                Log.d("TAG", "ADD id is---->"+newId);
        }
        });
        bt_update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.notepads.provider/"+"/notes/"+newId);
                Log.d("TAG", "update id id is---->"+newId);
                ContentValues contentValues = new ContentValues();
                contentValues.put("title","Frank");
                contentValues.put("context","this is frank");
                contentValues.put("time","12.12");
                getContentResolver().update(uri,contentValues,null,null);
                Log.d("TAG", "update----->yes");
            }
        });
        bt_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.notepads.provider/"+"/notes/"+newId);
                getContentResolver().delete(uri,null,null);
                Log.d("TAG", "delete----->yes");
            }
        });
    }
}
