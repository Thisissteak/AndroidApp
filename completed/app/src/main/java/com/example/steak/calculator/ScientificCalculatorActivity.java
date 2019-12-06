package com.example.steak.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.luozenglin.calculator.R;
import com.example.steak.service.ScientificCalculator;

public class ScientificCalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_scientific_layout);
        ScientificCalculator scientificCalculator = new ScientificCalculator(this);
    }
}
