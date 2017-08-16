package com.miaml.demo.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.miaml.demo.R;
import com.miaml.demo.util.ShaderHelper;
import com.miaml.demo.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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


public class AirHockeyRenderer implements GLSurfaceView.Renderer {


    private Context mContext;

    //顶点
    float[] tableVertices = {
            0f,0f,
            0f,14f,
            9f,14f,
            9f,0f

    };

    float[] tableVerticesWithTriangles ={

            //triangle 1
            -0.5f,-0.5f,
            0.5f,0.5f,
            -0.5f,0.5f,

            //triangle 2
            -0.5f,-0.5f,
            0.5f,-0.5f,
             0.5f,0.5f,

            //line 1
            -0.5f,0f,
             0.5f,0f,

            //mallets
            0f,-0.25f,
            0f,0.25f,
            0f,0f,

            //broad color
            0.5f,0.5f,
            -0.5f,0.5f,

    };

    float[] tableVerticesWithTriangles2 ={

            //triangle 1
            0f,0f,
            9f,14f,
            0f,14f,

            //triangle 2
            0f,0f,
            9f,0f,
            9f,14f,

            //line 1
            0f,7f,
            9f,7f,

            //mallets
            4.5f,2f,
            4.5f,23f

    };
    private static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexData;
    private final String mVertexShaderSource;
    private final String mFragmentShaderSource;
    private int mProgram;

    private static final String U_COLOR = "u_Color";
    private int uColorLocation;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    private static final int POSTION_COMPONET_COUNT = 2;
    public AirHockeyRenderer(Context context) {
        mContext = context;

        vertexData = ByteBuffer
                .allocateDirect(BYTES_PER_FLOAT * tableVerticesWithTriangles.length)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer(); //环境转换  把虚拟机环境 的data ---》 本地环境的data

        vertexData.put(tableVerticesWithTriangles); //把数据从Dalvik 的内存copy到本地内存


        mVertexShaderSource = TextResourceReader.readTextFile4Resource(context, R.raw.simple_vertex_shader);

        mFragmentShaderSource = TextResourceReader.readTextFile4Resource(context, R.raw.simple_fragment_shader);



    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        int vertexShader = ShaderHelper.compileVertexShader(mVertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(mFragmentShaderSource);

        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        GLES20.glUseProgram(mProgram); //t通知opengl 在绘制任何东西到屏幕使用这里定义的程序
        uColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR); //uniform设置将要绘制的颜色 获取uniform 的位置，但更新unfirom的时候使用它
        aPositionLocation = GLES20.glGetAttribLocation(mProgram,A_POSITION); //获取属性的位置 ，有了这个位置，opengl就知道到哪里找到这个属性对应的数据


        //关联属性与顶点属性数组

        vertexData.position(0);//设置在数据的开头读取

        /**
         * @param index
         * 通过调用opengl就知道从哪里读取
         */
        GLES20.glVertexAttribPointer(aPositionLocation,POSTION_COMPONET_COUNT,GLES20.GL_FLOAT,false,0,vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation); //通过调用opengl就知道从哪里读取






    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //开始绘制
        //1.绘制桌子

        GLES20.glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f);
        /**
         * @param mode GL_TRIANGLES 绘制三角形 GL_POINTS 绘制点 GL_LINES 绘制线
         * @param first 从顶点数组的开头读取顶点
         * @param count 读取顶点数组多少个点
         */
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);

        //2.绘制线

        GLES20.glUniform4f(uColorLocation,0.0f,0.0f,1.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES,6,2);

        //3.绘制点
        GLES20.glUniform4f(uColorLocation,0.0f,0.0f,1.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,8,1);

        GLES20.glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,9,1);

        GLES20.glUniform4f(uColorLocation,0.0f,1.0f,0.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,10,1);

        GLES20.glUniform4f(uColorLocation,0.5f,1.0f,0.5f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES,11,2);
    }
}
