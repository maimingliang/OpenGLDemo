package com.miaml.demo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.miaml.demo.render.AirHockeyRenderer2;
import com.miaml.demo.util.Utils;

public class AirHockeyActivity2 extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;
    private boolean rendererSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLSurfaceView = new GLSurfaceView(this);
        initGl();
        setContentView(mGLSurfaceView);
    }

    private void initGl() {

        if (!Utils.isSuppostEs2(this)) {
            Toast.makeText(this,"does suppost es 2.0 ",Toast.LENGTH_SHORT).show();
            return;
        }

        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(new AirHockeyRenderer2(this));
        rendererSet = true;


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rendererSet) {
            mGLSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (rendererSet) {
            mGLSurfaceView.onPause();
        }
    }

}
