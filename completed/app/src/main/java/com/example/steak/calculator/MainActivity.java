package com.example.steak.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.example.steak.Converter.Activity_Converter;
import com.example.luozenglin.calculator.R;
import com.example.steak.service.StandardCalculator;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 填充menu的main.xml文件; 给action bar添加条目
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.help:
                Toast.makeText(MainActivity.this,"This is help!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.convert:
//                converter.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Converter.class);
//                        intent.setClass(com.jtlab.scientificcalculator.MainActivity.this,Activity_Converter.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_standard_layout);
        StandardCalculator standard = new StandardCalculator(this);
        try {
            ViewConfiguration config = ViewConfiguration.get(this);//得到一个已经设置好设备的显示密度的对象
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");//反射获取其中的方法sHasPermanentMenuKey()，他的作用是报告设备的菜单是否对用户可用，如果不可用可强制可视化。
            if (menuKeyField != null) {
                //强制设置参数,让其重绘三个点
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
