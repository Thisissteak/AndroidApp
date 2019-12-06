package com.example.steak.Converter;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.luozenglin.calculator.R;


public class Activity_Converter extends AppCompatActivity {

    private int input_flag;   //输入进制标志位
    private int output_flag;   //输出进制标志位
    private EditText input_text;
    private EditText output_text;
    private Button change;
    private RadioGroup input_group;
    private RadioGroup  output_group;
    private RadioButton input_two;
    private RadioButton input_eight;
    private RadioButton input_ten;
    private RadioButton input_sixteen;
    private RadioButton input_mile;
    private RadioButton input_lfm;
    private RadioButton output_two;
    private RadioButton output_eight;
    private RadioButton output_ten;
    private RadioButton output_sixteen;
    private RadioButton output_ft;
    private RadioButton output_lfft;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        choose();  //保存用户设置
    }

    //保存用户的按键选择
    public void choose(){
        //建立监听器
        input_text = (EditText) findViewById(R.id.input_text);
        output_text = (EditText) findViewById(R.id.output_text);
        change = (Button) findViewById(R.id.change);
        input_group = (RadioGroup) findViewById(R.id.input_group);
        output_group = (RadioGroup) findViewById(R.id.output_group);
        input_two = (RadioButton) findViewById(R.id.input_two);
        input_eight = (RadioButton) findViewById(R.id.input_eight);
        input_ten = (RadioButton) findViewById(R.id.input_ten);
        input_sixteen = (RadioButton) findViewById(R.id.input_sixteen);
        input_mile = (RadioButton)findViewById(R.id.input_mile);
        input_lfm = (RadioButton)findViewById(R.id.input_lfm);
        output_two = (RadioButton) findViewById(R.id.output_two);
        output_eight = (RadioButton) findViewById(R.id.output_eight);
        output_ten = (RadioButton) findViewById(R.id.output_ten);
        output_sixteen = (RadioButton) findViewById(R.id.output_sixteen);
        output_ft = (RadioButton) findViewById(R.id.output_ft);
        output_lfft = (RadioButton) findViewById(R.id.output_lfft);

        //设置初始状态 默认十进制转十进制
        input_ten.setChecked(true);
        output_ten.setChecked(true);
        input_flag = 10;
        output_flag = 10;

        //进制输入RadioButton检测
        input_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId){
                checkedId = group.getCheckedRadioButtonId();   //获取改变按钮ID
                if(checkedId == input_two.getId())  //二进制
                    input_flag = 2;
                else if(checkedId == input_eight.getId())
                    input_flag = 8;
                else if(checkedId == input_ten.getId())
                    input_flag = 10;
                else if(checkedId == input_sixteen.getId())
                    input_flag = 16;
                else if(checkedId == input_mile.getId())
                    input_flag = 100;
                else if(checkedId == input_lfm.getId())
                    input_flag = 999;


            }
        });
        //进制输出监听器
        output_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId){
                checkedId = group.getCheckedRadioButtonId();   //获取改变按钮ID
                if(checkedId == output_two.getId())  //二进制
                    output_flag = 2;
                else if(checkedId == output_eight.getId())
                    output_flag = 8;
                else if(checkedId == output_ten.getId())
                    output_flag = 10;
                else if(checkedId == output_sixteen.getId())
                    output_flag = 16;
                else if(checkedId == output_ft.getId())
                    output_flag = 100;
                else if(checkedId == output_lfft.getId())
                    output_flag = 999;

            }
        });
        //转换按键监听器
        change.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                change();
            }
        });
    }

    public void change(){
        String str;
        int input_num = 0;
        double double_num = 0;
        char a = '0';
        int enable_flag = 0;
        String cha;

        String result = "";  //初始化
        int input_number = 0;
        cha = input_text.getText().toString();
        if(input_flag == 2) {
            for(int i = 0; i < cha.length(); i ++){
                a = cha.charAt(i);
                //判断是否有输入错误
                if(a >= '0' && a <= '1')
                    enable_flag = 0;
                else
                    enable_flag = 1;
            }
            if(enable_flag == 0) {
                str = Integer.valueOf(cha, 2).toString();  //二进制转十进制
                input_num = Integer.valueOf(str).intValue();
            }
            else{
                Toast.makeText(Activity_Converter.this,"输入错误",Toast.LENGTH_SHORT).show();
            }
        }
        else if(input_flag == 8){
            for(int i = 0; i < cha.length(); i ++){
                a = cha.charAt(i);
                //判断是否有输入错误
                if(a >= '0' && a <= '7')
                    enable_flag = 0;
                else
                    enable_flag = 1;
            }
            if(enable_flag == 0) {
                str = Integer.valueOf(cha, 8).toString();  //八进制转十进制
                input_num = Integer.valueOf(str).intValue();
            }
            else{
                Toast.makeText(Activity_Converter.this,"输入错误",Toast.LENGTH_SHORT).show();
            }
        }
        /*************米转换******************/
        else if(input_flag == 100){
            for(int i = 0; i < cha.length(); i ++){
                a = cha.charAt(i);
                //判断是否有输入错误
                if(a >= '0' && a <= '7')
                    enable_flag = 0;

                else
                    enable_flag = 1;
            }
            if(enable_flag == 0) {
               Double number = Double.valueOf(cha).doubleValue();
                Log.d("TAG", "number is"+number);

                double_num= 3.2808*number;
                Log.d("TAG", "double is"+double_num);

            }
            else{
                Toast.makeText(Activity_Converter.this,"输入错误",Toast.LENGTH_SHORT).show();
            }
        }
        /*******************************/
        /*************体积******************/
        else if(input_flag == 999){
            for(int i = 0; i < cha.length(); i ++){
                a = cha.charAt(i);
                //判断是否有输入错误
                if(a >= '0' && a <= '7')
                    enable_flag = 0;
                else
                    enable_flag = 1;
            }
            if(enable_flag == 0) {
                Double number = Double.valueOf(cha).doubleValue();
                Log.d("TAG", "number is"+number);

                double_num= 35.3147*number;
                Log.d("TAG", "double is"+double_num);
            }
            else{
                Toast.makeText(Activity_Converter.this,"输入错误",Toast.LENGTH_SHORT).show();
            }
        }
        /*******************************/
        else if(input_flag == 10){
            for(int i = 0; i < cha.length(); i ++){
                a = cha.charAt(i);
                //判断是否有输入错误
                if(a >= '0' && a <= '9')
                    enable_flag = 0;
                else
                    enable_flag = 1;
            }
            if(enable_flag == 0) {
                str = cha;
                input_num = Integer.valueOf(str).intValue();
            }
            else{
                Toast.makeText(Activity_Converter.this,"输入错误",Toast.LENGTH_SHORT).show();
            }
        }
        else if(input_flag == 16){
            for(int i = 0; i < cha.length(); i ++){
                a = cha.charAt(i);
                //判断是否有输入错误
                if((a >= '0' && a <= '9') || (a >= 'a' && a <= 'f'))
                    enable_flag = 0;
                else
                    enable_flag = 1;
            }
            if(enable_flag == 0) {
                str = Integer.valueOf(cha, 16).toString();  //十六进制转十进制
                input_num = Integer.valueOf(str).intValue();
            }
            else{
                Toast.makeText(Activity_Converter.this,"输入错误",Toast.LENGTH_SHORT).show();
            }
        }

        if(output_flag == 2){
            result = Integer.toBinaryString(input_num);  //十进制转二进制
        }
        else if(output_flag == 8){
            result = Integer.toOctalString(input_num);  //十进制转八进制
        }
        else if(output_flag == 10){
            result = String.valueOf(input_num);
        }
        else if(output_flag == 16){
            result = Integer.toHexString(input_num);    //十进制转十六进制
        }
        else if(output_flag == 100){
            result = String.valueOf(double_num);
            Log.d("TAG", "result is"+result);
        }
        else if(output_flag == 999){
            result = String.valueOf(double_num);
        }

        if(enable_flag == 0)
            output_text.setText(result);  //必须先初始化
        else
            output_text.setText("error");

        enable_flag = 0;  //标志位清零

    }
}
