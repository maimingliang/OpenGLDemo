package com.miaml.demo;

import android.app.Application;
import android.content.Context;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

/**
 * 类       名:
 * 说       明:

 * version   0.1
 * date   2017/8/5
 * author   maimingliang
 */


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initFFmpegBinary(this);

    }

    private void initFFmpegBinary(Context context) {

        try {
            FFmpeg.getInstance(context).loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                }
            });

        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
        }
    }

}
