package com.miaml.demo.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;

/**
 * 类       名:
 * 说       明:
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
