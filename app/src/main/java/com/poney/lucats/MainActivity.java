package com.poney.lucats;

import android.os.Bundle;

import com.common.mvp.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}