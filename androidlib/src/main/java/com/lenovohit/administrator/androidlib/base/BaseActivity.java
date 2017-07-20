package com.lenovohit.administrator.androidlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by SharkChao on 2017-07-18.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        initView();
        initEvent();
    }
    public abstract void initVariables();
    public abstract void initView();
    public abstract void initEvent();
}
