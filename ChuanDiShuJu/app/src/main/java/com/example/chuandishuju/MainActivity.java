package com.example.chuandishuju;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode) {
            case 1:
                if (resultCode==RESULT_OK){
                    String s = data.getStringExtra("data_return");
                 Log.d("TAG","MY INFORMATION --> "+s);
//                    Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                }
//                String s = data.getStringExtra("data_return");
//                Log.d("TAG","MY INFORMATION --> "+s);
                    break;
                default:
                    Log.d("TAG","MY INFORMATION --> ");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 =findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Intent intent = new Intent(MainActivity.this,AnotherActivity.class);
//               String s = "Hi!!";
//               intent.putExtra("extra_data",s);
//               startActivity(intent);
               Intent intent = new Intent(MainActivity.this,AnotherActivity.class);
               startActivityForResult(intent,1);
            }
        });
    }
}
