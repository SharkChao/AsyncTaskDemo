package com.lenovohit.administrator.asynctaskdemo.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lenovohit.administrator.androidlib.base.BaseActivity;
import com.lenovohit.administrator.androidlib.net.BaseAsyncTask;
import com.lenovohit.administrator.asynctaskdemo.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private Button mBtnSend;
    private TextView mTvContent;

    //从intent获取的数据，一些初始化数据的操作
    @Override
    public void initVariables() {
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        mBtnSend = (Button) findViewById(R.id.btnSend);
        mTvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void initEvent() {
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String>map=new HashMap<String, String>();
                map.put("email","11");
                map.put("contact","18757574669");
                map.put("content","非常厉害啊");
                MainAsyncTask task=new MainAsyncTask(map);
                task.execute("http://10.63.201.105:8003/NeweHealthServices/api/System/AddUserFeedback",BaseAsyncTask.POST);
            }
        });
    }
    class MainAsyncTask extends BaseAsyncTask {

        public MainAsyncTask(Map<String, String> map) {
            super(map);
        }

        @Override
        public void success(String connect) {
            mTvContent.setText(connect);
        }

        @Override
        public void fail(String errorMessage) {
            mTvContent.setText(errorMessage);
        }
    }
}
