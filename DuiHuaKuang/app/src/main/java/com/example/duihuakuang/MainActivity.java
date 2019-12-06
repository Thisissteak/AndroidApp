package com.example.duihuakuang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1=(Button)findViewById(R.id.button1);
        Button button2=(Button)findViewById(R.id.button2);

//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText editText1=(EditText)findViewById(R.id.text1);
//                EditText editText2=(EditText)findViewById(R.id.text2);
//                String a =editText1.getText().toString();
//                String b =editText2.getText().toString();
//                if (a.equals("abc"))
//                    if (b.equals("123"))
//                        Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
//                        else
//                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();
//            }
//        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.denglu,null);
                editText1 = view.findViewById(R.id.text1);
                editText2 = view.findViewById(R.id.text2);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("登录").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"您取消了登录",Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                String a = editText1.getText().toString();
                String b = editText2.getText().toString();
                if(a.equals("abc")&&b.equals("123")){
                    Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();
                }
                    }
                });
                builder.setView(view).show();

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT);
                AlertDialog.Builder  ex1 = new AlertDialog.Builder(MainActivity.this);
                ex1.setTitle("123").show();

            }
        });


    }
}
