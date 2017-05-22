package com.smart.androidutils.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smart.androidutils.R;

public class SPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        setTitle("SP工具类");
    }
}
