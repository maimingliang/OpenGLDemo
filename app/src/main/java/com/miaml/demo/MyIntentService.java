package com.miaml.demo;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

/**
 * 类       名:
 * 说       明:
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
