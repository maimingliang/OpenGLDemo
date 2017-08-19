package com.miaml.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String mVideoPath;
    private ProgressBar mPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           mVideoPath = "/storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1491139171727.mp4";

        mPb = (ProgressBar) findViewById(R.id.pb);

        findViewById(R.id.speed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starAnimation();
//                Intent intent = new Intent(MainActivity.this, OpenGLDemoActivity.class);
//                startActivity(intent);
             }
        });
        findViewById(R.id.airHockey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AirHockeyActivity.class);
                startActivity(intent);
             }
        });
        findViewById(R.id.airHockey2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AirHockeyActivity2.class);
                startActivity(intent);
             }
        });
    }


    public void starAnimation() {
        TextView text = (TextView) findViewById(R.id.text);

        // 初始化需要加载的动画资源
        Animation animation = AnimationUtils
                .loadAnimation(this, R.anim.fade_out_fast);

        // 将TextView执行Animation动画
        text.startAnimation(animation);
    }


    public void test() {
        String deviceId = DeviceUtil.getDeviceId(this);
        Log.e("tag", "---- id = " + deviceId);
        String androidID = DeviceUtil.getAndroidID(this);

        Log.e("tag", "---- androidID = " + androidID);
    }

    private void speedClick() {


        String outPath = getOutPath(mVideoPath)+"_speed.mp4";



        String speedCommandStr = getSpeedCommandStr(mVideoPath, 2, outPath);

        String ttfFilePath = "file:///android_asset/font/Athelas-W01-Italic.ttf";

        final StringBuilder builder = new StringBuilder();

        builder.append("ffmpeg -y -i /storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1491139171727.mp4");
        builder.append(" -vf drawtext=");
        builder.append(ttfFilePath);
        builder.append(":text=HelloWorld:x=(w-tw)/2:y=(h-th)/2 ");
        builder.append("/sdcard/drawtext.mp4");

        final long s = System.currentTimeMillis();

        try {
            FFmpeg.getInstance(getApplicationContext()).execute(builder.toString().split(" "),new ExecuteBinaryResponseHandler(){

                @Override
                public void onStart() {
                    super.onStart();
                    mPb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSuccess(String message) {
                    super.onSuccess(message);
                    Log.e("tag","onSuccess");
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    Log.e("tag","onFailure = " + message);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    long e = System.currentTimeMillis();
                    mPb.setVisibility(View.GONE);
                    Log.e("tag","onFinish  = " + (e-s)/1000);

                    Log.e("tag", "thread = " + Thread.currentThread());

                }


            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取输出路径
     * @param path
     * @return
     */
    @NonNull
    private String getOutPath(String path) {


        if (TextUtils.isEmpty(path)) {
            return null;
        }
        int i = path.lastIndexOf(".");

        String outPath = path.substring(0,i);


        return outPath;
    }
    /**
     * 改变视频的速度的ffmpeg命令 atempo【0.5,2】
     *  ffmpeg -i input.mkv -filter_complex "[0:v]setpts=0.5*PTS[v];[0:a]atempo=2.0[a]" -map "[v]" -map "[a]" output.mkv
     * @param videoPath
     * @param speed
     * @return
     */
    private String getSpeedCommandStr(String videoPath, float speed, String outPath) {

        if (TextUtils.isEmpty(videoPath) || TextUtils.isEmpty(outPath)) {
            return null;
        }


        String filter = String.format(Locale.getDefault(), "[0:v]setpts=%f*PTS[v];[0:a]atempo=%f[a]", 1/speed, speed);
        StringBuilder sb = new StringBuilder();
        sb.append("-i");
        sb.append(" "+videoPath);
        sb.append(" -filter_complex");
        sb.append(" "+filter);
        sb.append(" -map");
        sb.append(" [v]");
        sb.append(" -map");
        sb.append(" [a]");
        sb.append(" -b:v 3000k -g 25");
        sb.append(" -y");
        sb.append(" "+outPath);




        return sb.toString();
    }



}
