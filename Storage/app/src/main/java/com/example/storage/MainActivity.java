package com.example.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText edit1;
    private EditText edit2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//       final EditText student_number=findViewById(R.id.text_number);//获取学号编辑框
//       final EditText password=findViewById(R.id.text_password);//获取密码编辑框
        edit1 = findViewById(R.id.text_number);
        edit2 = findViewById(R.id.text_password);
        Button bt_login = findViewById(R.id.login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText1 = edit1.getText().toString();
                String inputText2 = edit2.getText().toString();
                save(inputText1, "data1");
                save(inputText2, "data2");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }


            public void save(String inputText, String filename) {
                FileOutputStream out = null;//声明输出流
                BufferedWriter writer = null;//声明输入流
                try {
                    out = openFileOutput(filename, MODE_PRIVATE);
                    writer = new BufferedWriter(new OutputStreamWriter(out));
                    writer.write(inputText);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try
                    { if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };
            };


        });


    }
}