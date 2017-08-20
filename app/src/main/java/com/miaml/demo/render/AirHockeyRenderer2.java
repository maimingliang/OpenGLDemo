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
 * date   2017/8/15
 * author   maimingliang
 */


public class AirHockeyRenderer2 implements GLSurfaceView.Renderer {


    private Context mContext;

    //顶点
    float[] tableVertices = {
            0f,0f,
            0f,14f,
            9f,14f,
            9f,0f

    };

    float[] tableVerticesWithTriangles ={


            //Order of coordinates:X,Y,R,G,B


            //Triangle Fan
            0f,0f,1f,1f,1f,
            -0.5f,-0.5f,0.7f,0.7f,0.7f,
            0.5f,-0.5f,0.7f,0.7f,0.7f,
            0.5f,0.5f,0.7f,0.7f,0.7f,
            -0.5f,0.5f,0.7f,0.7f,0.7f,
            -0.5f,-0.5f,0.7f,0.7f,0.7f,

            -0.5f,0f,1f,0f,0f,
            0.5f,0f,1f,0f,0f,

            0f,-0.25f,0f,0f,1f,
            0f,0.25f,1f,0f,0f

    };


    private static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexData;
    private final String mVertexShaderSource;
    private final String mFragmentShaderSource;
    private int mProgram;

    private static final String U_COLOR = "u_Color";
    private int uColorLocation;

    private static final String A_POSITION = "a_Position";
    private static final String A_COLOR = "a_Color";
    private int aPositionLocation;
    private int aColorLocation;

    private static final int POSTION_COMPONET_COUNT = 2; //位置分量 x y
    private static final int COLOR_COMPONENT_COUNT = 3; // 颜色分量 RGB

    private static final int STRIDE = (POSTION_COMPONET_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT; //夸距，每个位置之间的字节数

    public AirHockeyRenderer2(Context context) {
        mContext = context;

        vertexData = ByteBuffer
                .allocateDirect(BYTES_PER_FLOAT * tableVerticesWithTriangles.length)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer(); //环境转换  把虚拟机环境 的data ---》 本地环境的data

        vertexData.put(tableVerticesWithTriangles); //把数据从Dalvik 的内存copy到本地内存


        mVertexShaderSource = TextResourceReader.readTextFile4Resource(context, R.raw.simple_vertex_shader2);

        mFragmentShaderSource = TextResourceReader.readTextFile4Resource(context, R.raw.simple_fragment_shader2);



    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        int vertexShader = ShaderHelper.compileVertexShader(mVertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(mFragmentShaderSource);

        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        GLES20.glUseProgram(mProgram); //t通知opengl 在绘制任何东西到屏幕使用这里定义的程序

        aPositionLocation = GLES20.glGetAttribLocation(mProgram,A_POSITION); //获取属性的位置 ，有了这个位置，opengl就知道到哪里找到这个属性对应的数据
        aColorLocation = GLES20.glGetAttribLocation(mProgram,A_COLOR); //获取属性的位置 ，有了这个位置，opengl就知道到哪里找到这个属性对应的数据


        //关联属性与顶点属性数组
        vertexData.position(0);//设置数据读取的位置
        /**
         * @param index
         * 通过调用opengl就知道从哪里读取
         */
        GLES20.glVertexAttribPointer(aPositionLocation,POSTION_COMPONET_COUNT,GLES20.GL_FLOAT,false,STRIDE,vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation); //通过调用opengl就知道从哪里读取

        vertexData.put(POSTION_COMPONET_COUNT);//设置数据读取的位置
        GLES20.glVertexAttribPointer(aColorLocation,COLOR_COMPONENT_COUNT,GLES20.GL_FLOAT,false,STRIDE,vertexData);
        GLES20.glEnableVertexAttribArray(aColorLocation); //通过调用opengl就知道从哪里读取




    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,6);

//        //开始绘制
//        //1.绘制桌子
//
//        GLES20.glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f);
//        /**
//         * @param mode GL_TRIANGLES 绘制三角形 GL_POINTS 绘制点 GL_LINES 绘制线
//         * @param first 从顶点数组的开头读取顶点
//         * @param count 读取顶点数组多少个点
//         */
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);
//
        //2.绘制线

         GLES20.glDrawArrays(GLES20.GL_LINES,6,2);

        //3.绘制点
         GLES20.glDrawArrays(GLES20.GL_POINTS,8,1);

         GLES20.glDrawArrays(GLES20.GL_POINTS,9,1);


    }
}
