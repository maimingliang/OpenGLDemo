package com.miaml.demo.util;

import android.opengl.GLES20;
import android.util.Log;

/**
 * 类       名:
 * 说       明:
 * date   2017/8/15
 * author   maimingliang
 */


public class ShaderHelper {

    public static int compileVertexShader(String shaderCode) {
            return compileShader(GLES20.GL_VERTEX_SHADER,shaderCode);
    }


    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    /**
     * 链接程序 通过opengl程序来链接 顶点着色器和片段着色器，使其运行
     * @param vertexShaderId
     * @param fragmentShaderId
     * @return
     */
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {

        int programObjectId = GLES20.glCreateProgram();

        if(programObjectId == 0){
            Log.e("tag"," cloud not create new program");
            return 0;
        }

        //把着色器附上 opengl程序
        GLES20.glAttachShader(programObjectId,vertexShaderId);
        GLES20.glAttachShader(programObjectId,fragmentShaderId);

        GLES20.glLinkProgram(programObjectId);
//        final int[] linkStatus = new int[1];
//        GLES20.glGetProgramiv(programObjectId,GLES20.GL_LINK_STATUS,linkStatus,0);
        if (!validateProgram(programObjectId)) {

            GLES20.glDeleteProgram(programObjectId);
            Log.e("tag"," link program fail ");
            return 0;
        }

        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        GLES20.glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId,GLES20.GL_VALIDATE_STATUS,validateStatus,0);
        Log.e("tag", "validateStatus[0] = "+ validateStatus[0]+"  ----- program info log = " + GLES20.glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }

    private static int compileShader(int type, String shaderCode) {


        int shader = GLES20.glCreateShader(type);

        if (shader == 0) {
            Log.e("ShaderHelper", "----could not create shader");
            return 0;
        }

        GLES20.glShaderSource(shader,shaderCode);
        GLES20.glCompileShader(shader);

        //取出编译状态
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,compileStatus,0);

        Log.e("ShaderHelper","==== shadercode =" + shaderCode +"  ------ info = " +GLES20.glGetShaderInfoLog(shader));


        if (compileStatus[0] == 0) {
            GLES20.glDeleteShader(shader);
            Log.e("ShaderHelper", "----compile fail");
            return 0;
        }
        return  shader;
    }

}
