package com.miaml.demo.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;

/**
 * 类       名:
 * 说       明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright © 2017
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/8/15
 * author   maimingliang
 */


public class Utils {


    public static boolean isSuppostEs2(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        ConfigurationInfo deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo();

        int version = deviceConfigurationInfo.reqGlEsVersion;

        if(version >= 0x20000){
            return true;
        }

        return false;
    }
}
