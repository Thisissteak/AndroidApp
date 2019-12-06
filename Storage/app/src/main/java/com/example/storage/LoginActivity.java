package com.example.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class LoginActivity extends AppCompatActivity {
    private EditText edit1;
    private EditText edit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button bt2 = findViewById(R.id.read);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit1 = findViewById(R.id.login_nb);
                edit2 = findViewById(R.id.login_pw);
                String a = load("data1");
                String b = load("data2");
                edit1.setText(a);
                edit2.setText(b);
                Toast.makeText(LoginActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
            }

            private String load(String data) {
                FileInputStream in = null;
                BufferedReader reader = null;
                StringBuilder content = new StringBuilder();
                try {
                    in = openFileInput(data);
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return content.toString();

            }

//        private String load(String filename){
//            FileInputStream in = null;
//            BufferedReader reader = null;
//            StringBuilder content = new StringBuilder();
//            try {
//                in = openFileInput(filename);
//                reader = new BufferedReader(new InputStreamReader(in));
//                String line ="";
//                while ((line = reader.readLine())!=null){
//                    content.append(line);
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }finally {
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }return content.toString();
//
//        }

        });
    }
}
