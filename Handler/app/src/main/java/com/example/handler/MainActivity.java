package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView text;
    public static final int UPDATE_TEXT = 1;
    private Button button;


    //    private Handler handler = new Handler();{
//        public void handleMessage(Message msg){
//            switch (mag.what){
//                case UPDATE_TEXT:
//                    text.setText("我改变了");
//                    break;
//                    default:
//                        break;
//            }
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button1);
        text = findViewById(R.id.text1);
        button.setOnClickListener(this);
    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    Log.d("TAG","handMessage--->");
                    text.setText("Changed");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                    Message message = new Message();
                    message.what = UPDATE_TEXT;
                         Log.d("TAG","onClick--->");
                    handler.sendMessage(message);
                     }
                 }).start();
                 break;
                 default:
                     break;

        }
    }
}

