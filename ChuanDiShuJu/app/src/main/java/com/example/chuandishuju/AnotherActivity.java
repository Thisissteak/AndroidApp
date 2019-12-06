package com.example.chuandishuju;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AnotherActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("data_return","Hello FirstActivity");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
//        Intent intent = getIntent();
//        String data =  intent.getStringExtra("extra_data");
//        Log.d("TAG", data);
//        Toast.makeText(AnotherActivity.this,data,Toast.LENGTH_SHORT).show();
        Button bt2 = findViewById(R.id.button_2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return","Hello FirstAcitivity");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
