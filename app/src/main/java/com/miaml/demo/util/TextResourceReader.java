package com.miaml.demo.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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


public class TextResourceReader {

    public static String readTextFile4Resource(Context context,int resourceId) {

        StringBuilder body = new StringBuilder();
        try {

            InputStream inputStream = context.getResources().openRawResource(resourceId);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null){
                body.append(nextLine);
                body.append("\n");

            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return body.toString();
    }
}
