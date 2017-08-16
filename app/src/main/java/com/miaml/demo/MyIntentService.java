package com.miaml.demo;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

/**
 * 类       名:
 * 说       明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright © 2017
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/8/7
 * author   maimingliang
 */


public class MyIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent == null) {
            return;
        }


        String cmd = intent.getStringExtra("cmd");

        if (TextUtils.isEmpty(cmd)) {
            return;
        }

        String[] cmds = cmd.split(" ");




    }
}
